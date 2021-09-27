package com.android.carromking.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button verifyAndProceed = view.findViewById(R.id.verifyAndProceedPaytm);
        EditText paytmOtp = view.findViewById(R.id.PaytmOtpField);
        verifyAndProceed.setEnabled(false);
        verifyAndProceed.setClickable(false);
        verifyAndProceed.setBackgroundColor(getActivity().getColor(R.color.button_grey));

        verifyAndProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.showGreenTickPaytm();
                dismiss();
            }
        });

        paytmOtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(paytmOtp.getText().toString().trim().length() == 6) {
                    verifyAndProceed.setClickable(true);
                    verifyAndProceed.setEnabled(true);
                    verifyAndProceed.setBackgroundColor(getActivity().getColor(R.color.blue));
                } else {
                    verifyAndProceed.setClickable(false);
                    verifyAndProceed.setEnabled(false);
                    verifyAndProceed.setBackgroundColor(getActivity().getColor(R.color.button_grey));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    public interface greenTickPaytm{
        void showGreenTickPaytm();
    }

    public void ShowGreenTickPaytm(greenTickPaytm listener){
        this.listener = listener;
    }
}
