package com.entwik.carromcash.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.entwik.carromcash.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class EnterUPI_Bottomsheet extends BottomSheetDialogFragment {
    SharedPreferences sp;
    private greenTickUpi listener;
    EditText etUpiId;
    Button verifyAndProceedUpi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.enter_upi_id, container, false);
        verifyAndProceedUpi = v.findViewById(R.id.verifyAndProceedUpi);
        etUpiId = v.findViewById(R.id.etUpiId);

        sp = v.getContext().getSharedPreferences(getString(R.string.TAG), Context.MODE_PRIVATE);

        verifyAndProceedUpi.setClickable(false);
        verifyAndProceedUpi.setBackgroundColor(v.getContext().getColor(R.color.button_grey));
        if(sp.getString("upi",null)!=null){
            etUpiId.setText(sp.getString("upi",null));
            changeButton(v);
        }

        etUpiId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    changeButton(v);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        verifyAndProceedUpi.setOnClickListener(v1 -> {
            listener.showGreenTickUpi();
            sp.edit().putString("upi",etUpiId.getText().toString().trim()).apply();
            dismiss();
        });

        return v;
    }

    public interface greenTickUpi{
        void showGreenTickUpi();
    }

    public void ShowGreenTickUpi(greenTickUpi listener){
        this.listener = listener;
    }

    public void changeButton(View v){
        String s = etUpiId.getText().toString().trim();
        if(!s.contains("@")  || s.length() < 6) {
            verifyAndProceedUpi.setClickable(false);
            verifyAndProceedUpi.setBackgroundColor(v.getContext().getColor(R.color.button_grey));
        } else {
            verifyAndProceedUpi.setClickable(true);
            verifyAndProceedUpi.setBackgroundColor(v.getContext().getColor(R.color.blue));
        }
    }
}
