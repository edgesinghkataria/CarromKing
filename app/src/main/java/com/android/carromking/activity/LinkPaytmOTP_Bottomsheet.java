package com.android.carromking.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.carromking.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class LinkPaytmOTP_Bottomsheet extends BottomSheetDialogFragment {

    private greenTickPaytm listener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.link_paytm_wallet_otp, container, false);

        Button verifyAndProceed = v.findViewById(R.id.verifyAndProceedPaytm);
        verifyAndProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.showGreenTickPaytm();
                dismiss();
            }
        });
        return v;
    }

    public interface greenTickPaytm{
        void showGreenTickPaytm();
    }

    public void ShowGreenTickPaytm(greenTickPaytm listener){
        this.listener = listener;
    }
}
