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
import com.entwik.carromcash.utils.ResultType;


public class ResultFragment extends Fragment {

    private ResultType mResult;
    private TextView mTvResult;
    private Button mCTA;
    private ImageView mWithdrawSuccessful;
    private TextView mCredited;

    ResultFragment(ResultType mResult) {
        this.mResult = mResult;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_result, container, false);
        mTvResult = v.findViewById(R.id.result_successful);
        mCTA = v.findViewById(R.id.keepPlaying);
        mWithdrawSuccessful = v.findViewById(R.id.imvWithdrawSuccessful);
        mCredited = v.findViewById(R.id.credited_24_hours);

        switch (mResult) {
            case DEPOSIT_SUCCESS:
                mTvResult.setText(getString(R.string.deposit_successful));
                mTvResult.setTextColor(getResources().getColor(R.color.green));
                mWithdrawSuccessful.setVisibility(View.VISIBLE);
                break;
            case DEPOSIT_PENDING:
                mTvResult.setText(getString(R.string.deposit_pending));
                mTvResult.setTextColor(getResources().getColor(R.color.yellow));
                break;
            case DEPOSIT_FAILURE:
                mTvResult.setText(getString(R.string.deposit_failed));
                mTvResult.setTextColor(getResources().getColor(R.color.red));
                mCTA.setText(getString(R.string.retry));
                break;
            case WITHDRAWAL_SUCCESS:
                mTvResult.setText(getString(R.string.withdrawal_successful));
                mTvResult.setTextColor(getResources().getColor(R.color.green));
                mWithdrawSuccessful.setVisibility(View.VISIBLE);
                mCredited.setVisibility(View.VISIBLE);
                break;
            case WITHDRAWAL_PENDING:
                mTvResult.setText(getString(R.string.withdrawal_pending));
                mTvResult.setTextColor(getResources().getColor(R.color.yellow));
                break;
            case WITHDRAWAL_FAILURE:
                mTvResult.setText(getString(R.string.withdrawal_failed));
                mTvResult.setTextColor(getResources().getColor(R.color.red));
                mCTA.setText(getString(R.string.retry));
                break;
        }

        mCTA.setOnClickListener(v1 -> {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new WalletFragment()).commit();
        });
        return v;
    }
}
