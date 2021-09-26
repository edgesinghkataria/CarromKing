package com.android.carromking.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.carromking.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class LinkPaytm_Bottomsheet extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.paytm_add_wallet, container, false);

        TextView cancel = v.findViewById(R.id.textView14);
        TextView proceed = v.findViewById(R.id.textView15);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinkPaytmOTP_Bottomsheet bottomSheet = new LinkPaytmOTP_Bottomsheet();
                bottomSheet.show(getActivity().getSupportFragmentManager(), "LinkPaytmOTP");
                dismiss();
            }
        });
        return v;
    }
}
