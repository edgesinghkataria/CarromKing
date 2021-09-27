package com.android.carromking.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.carromking.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddCash_Bottomsheet extends BottomSheetDialogFragment {
    Button button10, button20, button50, button100;
    EditText amountEditText;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_cash, container, false);
        button10 = v.findViewById(R.id.button4);
        button20 = v.findViewById(R.id.button5);
        button50 = v.findViewById(R.id.button6);
        button100 = v.findViewById(R.id.button7);
        amountEditText= v.findViewById(R.id.editTextNumber4);
        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountEditText.setText("10");
            }
        });
        button20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountEditText.setText("20");
            }
        });
        button50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountEditText.setText("50");
            }
        });
        button100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountEditText.setText("100");
            }
        });
        return v;
    }
}
