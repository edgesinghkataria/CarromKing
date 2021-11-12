package com.entwik.carromcash.activity;


import android.os.Bundle;


import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.entwik.carromcash.R;


public class DepositResultFragment extends Fragment {

    private int mResult;
    private TextView mTvDeposit;
    private Button mCTA;
    private ImageView mDepositSuccessful;

    DepositResultFragment(int mResult) {
        this.mResult = mResult;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.deposit_result, container, false);
        mTvDeposit = v.findViewById(R.id.deposit_successful);
        mCTA = v.findViewById(R.id.keepPlaying);
        mDepositSuccessful = v.findViewById(R.id.imvWithdrawSuccessful);

        switch (mResult) {
            case 0:
                mTvDeposit.setText(getString(R.string.deposit_successful));
                mTvDeposit.setTextColor(getResources().getColor(R.color.green));
                mDepositSuccessful.setVisibility(View.VISIBLE);
                break;
            case 1:
                mTvDeposit.setText(getString(R.string.deposit_pending));
                mTvDeposit.setTextColor(getResources().getColor(R.color.yellow));
                break;
            case 2:
                mTvDeposit.setText(getString(R.string.deposit_failed));
                mTvDeposit.setTextColor(getResources().getColor(R.color.red));
                mCTA.setText(getString(R.string.retry));
                break;
        }

        // Change behaviour with type of mResult
        mCTA.setOnClickListener(v1 -> {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new WalletFragment()).commit();
        });
        return v;
    }
}
