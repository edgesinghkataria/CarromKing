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

public class EnterUPI_Bottomsheet extends BottomSheetDialogFragment {

    private greenTickUpi listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.enter_upi_id, container, false);
        Button verifyAndProceedUpi = v.findViewById(R.id.verifyAndProceedUpi);
        verifyAndProceedUpi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.showGreenTickUpi();
                dismiss();
            }
        });

        return v;
    }

    public interface greenTickUpi{
        void showGreenTickUpi();
    }

    public void ShowGreenTickUpi(greenTickUpi listener){
        this.listener = listener;
    }
}
