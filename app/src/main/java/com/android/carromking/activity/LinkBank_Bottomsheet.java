package com.android.carromking.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.carromking.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class LinkBank_Bottomsheet extends BottomSheetDialogFragment {

    private greenTickBank listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.link_bank_account, container, false);
        Button verifyAndProceedButton = v.findViewById(R.id.verifyAndProceedBank);
        verifyAndProceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.showGreenTickBank();
                dismiss();
            }
        });
        return v;
    }

    public interface greenTickBank{
        void showGreenTickBank();
    }

    public void ShowGreenTickBank(greenTickBank listener){
        this.listener = listener;
    }
}
