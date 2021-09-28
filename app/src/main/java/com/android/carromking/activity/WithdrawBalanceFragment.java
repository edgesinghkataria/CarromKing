package com.android.carromking.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.carromking.R;
import com.android.carromking.models.local.LocalDataModel;
import com.google.gson.Gson;

public class WithdrawBalanceFragment extends Fragment {

    private homeIconHighlightTwo listener;
    private LocalDataModel localDataModel;
    final Gson gson = new Gson();
    SharedPreferences sp;
    String TAG;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_withdraw_balance, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TAG = getString(R.string.TAG);
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


        Button withdrawNow = view.findViewById(R.id.withdraw_now_button);
        TextView linkPaytm = view.findViewById(R.id.link_account_paytm);
        ImageView linkUPI = view.findViewById(R.id.arrow_upi);
        ImageView linkBank = view.findViewById(R.id.arrow_bank_transfer);
        LinearLayout greenTickAndArrowPaytm = view.findViewById(R.id.greenTickAndArrowPaytm);
        ImageView greenTickUpi = view.findViewById(R.id.greenTickUpi);
        ImageView greenTickBank = view.findViewById(R.id.greenTickBankTransfer);
        TextView withdrawableBalance = view.findViewById(R.id.withdrawble_balance_amount);
        TextView amountToWithdraw = view.findViewById(R.id.amount_to_withdraw);

        withdrawableBalance.setText( "₹ "+ localDataModel.getWinningBalance());
        amountToWithdraw.setText("₹ "+ localDataModel.getWinningBalance());

        withdrawNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WithdrawSuccessfulFragment withdrawSuccessfulFragment = new WithdrawSuccessfulFragment();
                withdrawSuccessfulFragment.HighLightHomeIcon(new WithdrawSuccessfulFragment.homeIconHighlight() {
                    @Override
                    public void highlightHomeIcon() {
                        listener.highlightHomeIconTwo();
                    }
                });

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        withdrawSuccessfulFragment).commit();
            }
        });

        linkPaytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinkPaytm_Bottomsheet bottomSheet = new LinkPaytm_Bottomsheet();
                bottomSheet.ShowGreenTickPaytmTwo(new LinkPaytm_Bottomsheet.greenTickPaytmTwo() {
                    @Override
                    public void showGreenTickPaytmTwo() {
                        greenTickAndArrowPaytm.setVisibility(View.VISIBLE);
                        linkPaytm.setVisibility(View.GONE);
                        greenTickBank.setVisibility(View.GONE);
                        greenTickUpi.setVisibility(View.GONE);
                    }
                });
                bottomSheet.show(getActivity().getSupportFragmentManager(), "LinkPaytm");
            }
        });

        linkUPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterUPI_Bottomsheet bottomSheet = new EnterUPI_Bottomsheet();
                bottomSheet.ShowGreenTickUpi(new EnterUPI_Bottomsheet.greenTickUpi() {
                    @Override
                    public void showGreenTickUpi() {
                        greenTickUpi.setVisibility(View.VISIBLE);
                        greenTickBank.setVisibility(View.GONE);
                        greenTickAndArrowPaytm.setVisibility(View.GONE);
                        linkPaytm.setVisibility(View.VISIBLE);
                    }
                });
                bottomSheet.show(getActivity().getSupportFragmentManager(), "LinkUPI");
            }
        });

        linkBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinkBank_Bottomsheet bottomSheet = new LinkBank_Bottomsheet();
                bottomSheet.ShowGreenTickBank(new LinkBank_Bottomsheet.greenTickBank() {
                    @Override
                    public void showGreenTickBank() {
                        greenTickBank.setVisibility(View.VISIBLE);
                        greenTickUpi.setVisibility(View.GONE);
                        linkPaytm.setVisibility(View.VISIBLE);
                        greenTickAndArrowPaytm.setVisibility(View.GONE);
                    }
                });
                bottomSheet.show(getActivity().getSupportFragmentManager(), "LinkBank");
            }
        });


    }

    public interface homeIconHighlightTwo{
        void highlightHomeIconTwo();
    }

    public void HighLightHomeIconTwo(homeIconHighlightTwo listener){
        this.listener = listener;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(Html.fromHtml("<font color=\"black\">" + "Withdraw Balance" + "</font>"));
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

}
