package com.android.carromking.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.carromking.ApiService;
import com.android.carromking.CustomProgressBar;
import com.android.carromking.MyApiEndpointInterface;
import com.android.carromking.R;
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
    TextView unPlayedAmount, winningAmount, cashBonus, totalBalance;
    private LocalDataModel localDataModel;
    final Gson gson = new Gson();

    CustomProgressBar progressBar;

    String TAG;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wallet, container,false);
        Button withdraw = v.findViewById(R.id.button_withdraw);
        TextView seeAllTransactions = v.findViewById(R.id.seeAllTransactions);
        Button addMoney = v.findViewById(R.id.button_addMoney);

        TAG = v.getContext().getString(R.string.TAG);

        progressBar = new CustomProgressBar(requireActivity());

        addMoney.setOnClickListener(v12 -> {
            AddCash_Bottomsheet bottomSheet = new AddCash_Bottomsheet();
            bottomSheet.show(requireActivity().getSupportFragmentManager(), "AddMoney");
        });

        withdraw.setOnClickListener(v13 -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new WithdrawBalanceFragment()).commit());

        seeAllTransactions.setOnClickListener(v1 ->
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HistoryFragment()).commit());
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sp = view.getContext().getSharedPreferences(TAG, Context.MODE_PRIVATE);

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


        unPlayedAmount = view.findViewById(R.id.unplayed_amount);
        winningAmount = view.findViewById(R.id.winning_amount);
        cashBonus = view.findViewById(R.id.cash_bonus);
        totalBalance = view.findViewById(R.id.wallet_balance);

        String unPlayedBalance = localDataModel.getDepositBalance();
        String winningBalance = localDataModel.getWinningBalance();
        String bonusBalance = localDataModel.getBonusBalance();

        unPlayedAmount.setText(unPlayedBalance);
        winningAmount.setText(winningBalance);
        cashBonus.setText(bonusBalance);
        totalBalance.setText(String.valueOf(Integer.parseInt(unPlayedBalance) + Integer.parseInt(winningBalance) + Integer.parseInt(bonusBalance)));

        progressBar.show();
        MyTask myTask = new MyTask();
        myTask.execute(view);

    }

    private class MyTask extends AsyncTask<View, Integer , WalletResponseDataModel>{
        @Override
        protected WalletResponseDataModel doInBackground(View... views) {
//            Context context = views[0].getContext();

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
                                    progressBar.hide();
                                    Log.d(TAG, "Wallet onResponse: " + body.getData().getUserId());
                                } else {
                                    progressBar.hide();
                                    Toast.makeText(getContext(), body.getError().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<WalletResponseModel> call, @NonNull Throwable t) {
                            progressBar.hide();
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

                String unPlayedBalance = localDataModel.getDepositBalance();
                String winningBalance = localDataModel.getWinningBalance();
                String bonusBalance = localDataModel.getBonusBalance();

                unPlayedAmount.setText(localDataModel.getDepositBalance());
                winningAmount.setText(localDataModel.getWinningBalance());
                cashBonus.setText(localDataModel.getBonusBalance());
                totalBalance.setText(String.valueOf(Integer.parseInt(unPlayedBalance) + Integer.parseInt(winningBalance) + Integer.parseInt(bonusBalance)));

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

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(Html.fromHtml("<font color=\"black\">" + "Wallet" + "</font>"));
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

}
