package com.android.carromking.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.carromking.R;

public class WithdrawBalanceFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_withdraw_balance, container, false);

        Button withdrawNow = v.findViewById(R.id.withdraw_now_button);
        TextView linkPaytm = v.findViewById(R.id.link_account_paytm);
        ImageView linkUPI = v.findViewById(R.id.arrow_upi);
        ImageView linkBank = v.findViewById(R.id.arrow_bank_transfer);
        LinearLayout greenTickAndArrowPaytm = v.findViewById(R.id.greenTickAndArrowPaytm);
        ImageView greenTickUpi = v.findViewById(R.id.greenTickUpi);
        ImageView greenTickBank = v.findViewById(R.id.greenTickBankTransfer);

        withdrawNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new WithdrawSuccessfulFragment()).commit();
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

        return v;
    }


}
