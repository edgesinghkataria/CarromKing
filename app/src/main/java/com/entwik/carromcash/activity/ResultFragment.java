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


public class ResultFragment extends Fragment {

    private int mResult;
    private TextView mTvWithdraw;
    private Button mCTA;
    private ImageView mWithdrawSuccessful;
    private TextView mCredited;

    ResultFragment(int mResult) {
        this.mResult = mResult;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_result, container, false);
        mTvWithdraw = v.findViewById(R.id.withdrawal_successful);
        mCTA = v.findViewById(R.id.keepPlaying);
        mWithdrawSuccessful = v.findViewById(R.id.imvWithdrawSuccessful);
        mCredited = v.findViewById(R.id.credited_24_hours);

        switch (mResult) {
            case 0:
                mTvWithdraw.setText(getString(R.string.withdrawal_successful));
                mTvWithdraw.setTextColor(getResources().getColor(R.color.green));
                mWithdrawSuccessful.setVisibility(View.VISIBLE);
                mCredited.setVisibility(View.VISIBLE);
                break;
            case 1:
                mTvWithdraw.setText(getString(R.string.withdrawal_pending));
                mTvWithdraw.setTextColor(getResources().getColor(R.color.yellow));
                break;
            case 2:
                mTvWithdraw.setText(getString(R.string.withdrawal_failed));
                mTvWithdraw.setTextColor(getResources().getColor(R.color.red));
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
