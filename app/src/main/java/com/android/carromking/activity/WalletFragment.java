package com.android.carromking.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
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

import java.util.Objects;

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

    private homeIconHighlightThree listener;

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

        withdraw.setOnClickListener(v13 -> {
            WithdrawBalanceFragment withdrawBalanceFragment = new WithdrawBalanceFragment();
            withdrawBalanceFragment.HighLightHomeIconTwo(new WithdrawBalanceFragment.homeIconHighlightTwo() {
                @Override
                public void highlightHomeIconTwo() {
                    listener.highlightHomeIconThree();
                }
            });
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                withdrawBalanceFragment).addToBackStack(null).commit();
        });

        seeAllTransactions.setOnClickListener(v1 ->
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HistoryFragment()).addToBackStack(null).commit());


        sp = v.getContext().getSharedPreferences(TAG, Context.MODE_PRIVATE);

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


        unPlayedAmount = v.findViewById(R.id.unplayed_amount);
        winningAmount = v.findViewById(R.id.winning_amount);
        cashBonus = v.findViewById(R.id.cash_bonus);
        totalBalance = v.findViewById(R.id.wallet_balance);

        String unPlayedBalance = localDataModel.getDepositBalance();
        String winningBalance = localDataModel.getWinningBalance();
        String bonusBalance = localDataModel.getBonusBalance();

        unPlayedAmount.setText(unPlayedBalance);
        winningAmount.setText(winningBalance);
        cashBonus.setText(bonusBalance);
        totalBalance.setText(String.valueOf(Integer.parseInt(unPlayedBalance) + Integer.parseInt(winningBalance) + Integer.parseInt(bonusBalance)));

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getWalletData();
    }


    private void updateLocal(WalletResponseDataModel wallet) {
        localDataModel.setDepositBalance(String.valueOf(wallet.getDepositBalance()));
        localDataModel.setWinningBalance(String.valueOf(wallet.getWinningBalance()));
        localDataModel.setBonusBalance(String.valueOf(wallet.getBonusBalance()));

        sp.edit().putString("local", new Gson().toJson(localDataModel)).apply();
    }

    public interface homeIconHighlightThree{
        void highlightHomeIconThree();
    }

    public void HighLightHomeIconThree(homeIconHighlightThree listener){
        this.listener = listener;
    }

    private void getWalletData() {
        progressBar.show();
            ApiService apiService = new ApiService();
            MyApiEndpointInterface apiEndpointInterface = apiService.
                    getApiServiceForInterceptor(apiService.getInterceptor(sp.getString("token", null)));

        if(!apiService.internetIsConnected()) {
            progressBar.hide();
            Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT).show();
        } else {
            apiEndpointInterface.getWalletData()
                    .enqueue(new Callback<WalletResponseModel>() {
                        @Override
                        public void onResponse(@NonNull Call<WalletResponseModel> call, @NonNull Response<WalletResponseModel> response) {
                            WalletResponseModel body = response.body();
                            if(body!=null) {
                                if(body.isStatus()) {
                                    dataModel = body.getData();

                                    if(dataModel!=null) {
                                        updateLocal(dataModel);
                                        String unPlayedBalance = localDataModel.getDepositBalance();
                                        String winningBalance = localDataModel.getWinningBalance();
                                        String bonusBalance = localDataModel.getBonusBalance();

                                        unPlayedAmount.setText(localDataModel.getDepositBalance());
                                        winningAmount.setText(localDataModel.getWinningBalance());
                                        cashBonus.setText(localDataModel.getBonusBalance());
                                        totalBalance.setText(String.valueOf(Integer.parseInt(unPlayedBalance) + Integer.parseInt(winningBalance) + Integer.parseInt(bonusBalance)));

                                        progressBar.dismiss();
                                    } else {
                                        progressBar.dismiss();
                                        Toast.makeText(requireContext(), "Unable to refresh, try again later", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    progressBar.dismiss();
                                    Toast.makeText(getContext(), body.getError().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<WalletResponseModel> call, @NonNull Throwable t) {
                            progressBar.dismiss();
                        }
                    });
        }
    }

}
