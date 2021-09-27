package com.android.carromking.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.carromking.ApiService;
import com.android.carromking.MyApiEndpointInterface;
import com.android.carromking.R;
import com.android.carromking.models.common.UserDataModel;
import com.android.carromking.models.common.UserWalletDataModel;
import com.android.carromking.models.local.LocalDataModel;
import com.android.carromking.models.wallet.WalletResponseDataModel;
import com.android.carromking.models.wallet.WalletResponseModel;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletFragment extends Fragment {

    SharedPreferences sp;
    WalletResponseDataModel dataModel;
    TextView unplayedAmount, winningAmount, cashBonus, totalBalance;
    private LocalDataModel localDataModel;
    final Gson gson = new Gson();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wallet, container,false);
        Button withdraw = v.findViewById(R.id.button_withdraw);
        TextView seeAllTransactions = v.findViewById(R.id.seeAllTransactions);
        Button addMoney = v.findViewById(R.id.button_addMoney);

        addMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCash_Bottomsheet bottomSheet = new AddCash_Bottomsheet();
                bottomSheet.show(getActivity().getSupportFragmentManager(), "AddMoney");
            }
        });

        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new WithdrawBalanceFragment()).commit();
            }
        });

        seeAllTransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HistoryFragment()).commit();
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sp = view.getContext().getSharedPreferences(getString(R.string.TAG), Context.MODE_PRIVATE);

        LocalDataModel localDataModel1 =  new LocalDataModel(
                "1",
                getString(R.string.mobile_number),
                "",
                "silver",
                sp.getString("token", null),
                "0",
                "0",
                "0"
        );

        localDataModel = gson.fromJson(sp.getString("local", gson.toJson(localDataModel1)), LocalDataModel.class);


        unplayedAmount = view.findViewById(R.id.unplayed_amount);
        winningAmount = view.findViewById(R.id.winning_amount);
        cashBonus = view.findViewById(R.id.cash_bonus);
        totalBalance = view.findViewById(R.id.wallet_balance);

        String unplayedBalance = localDataModel.getDepositBalance();
        String winningBalance = localDataModel.getWinningBalance();
        String bonusBalance = localDataModel.getBonusBalance();

        unplayedAmount.setText(unplayedBalance);
        winningAmount.setText(winningBalance);
        cashBonus.setText(bonusBalance);
        totalBalance.setText(unplayedBalance + winningBalance + bonusBalance);

        MyTask myTask = new MyTask();
        myTask.execute(view);

    }

    private class MyTask extends AsyncTask<View, Integer , WalletResponseDataModel>{
        @Override
        protected WalletResponseDataModel doInBackground(View... views) {

            ApiService apiService = new ApiService();
            MyApiEndpointInterface apiEndpointInterface = apiService.
                    getApiServiceForInterceptor(apiService.getInterceptor(sp.getString("token", null)));

            apiEndpointInterface.getWalletData()
                    .enqueue(new Callback<WalletResponseModel>() {
                        @Override
                        public void onResponse(@NonNull Call<WalletResponseModel> call, @NonNull Response<WalletResponseModel> response) {
                            WalletResponseModel body = response.body();
                            if(body!=null) {
                                if(body.isStatus()) {
                                    dataModel = body.getData();
                                    Log.d(getContext().getString(R.string.TAG), "Wallet onResponse: " + body.getData().getUserId());
                                } else {
                                    Toast.makeText(getContext(), body.getError().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<WalletResponseModel> call, @NonNull Throwable t) {

                        }
                    });
            while (true) {
                if(dataModel!=null) {
                    return dataModel;
                }
            }

        }

        @Override
        protected void onPostExecute(WalletResponseDataModel walletResponseDataModel) {
            if(walletResponseDataModel!=null) {

                updateLocal(walletResponseDataModel);

                String unplayedBalance = localDataModel.getDepositBalance();
                String winningBalance = localDataModel.getWinningBalance();
                String bonusBalance = localDataModel.getBonusBalance();

                unplayedAmount.setText(unplayedBalance);
                winningAmount.setText(winningBalance);
                cashBonus.setText(bonusBalance);
                totalBalance.setText(unplayedBalance + winningBalance + bonusBalance);

            } else {
                Toast.makeText(requireContext(), "Unable to refresh, try again later", Toast.LENGTH_SHORT).show();
            }
        }


    }

    private void updateLocal(WalletResponseDataModel wallet) {
        localDataModel.setDepositBalance(String.valueOf(wallet.getDepositBalance()));
        localDataModel.setWinningBalance(String.valueOf(wallet.getWinningBalance()));
        localDataModel.setBonusBalance(String.valueOf(wallet.getBonusBalance()));

        sp.edit().putString("local", new Gson().toJson(localDataModel)).apply();
    }

}
