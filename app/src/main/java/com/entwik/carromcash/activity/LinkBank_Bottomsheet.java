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
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.entwik.carromcash.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class LinkBank_Bottomsheet extends BottomSheetDialogFragment {

    private greenTickBank listener;
    SharedPreferences sp;
    EditText etAccountNum, etConfirmAccNum, etIFSC, etName;
    CheckBox checkBox;
    Button verifyAndProceedButton;
    String TAG;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.link_bank_account, container, false);
        verifyAndProceedButton = v.findViewById(R.id.verifyAndProceedBank);

        TAG = getString(R.string.TAG);
        sp = getActivity().getSharedPreferences(TAG, Context.MODE_PRIVATE);

        etAccountNum = v.findViewById(R.id.etAccountNum);
        etConfirmAccNum = v.findViewById(R.id.etConfirmAccNum);
        etIFSC = v.findViewById(R.id.etIFSC);
        etName = v.findViewById(R.id.etName);
        checkBox = v.findViewById(R.id.checkBox2);

        verifyAndProceedButton.setClickable(false);
        verifyAndProceedButton.setBackgroundColor(v.getContext().getColor(R.color.button_grey));

        if(sp.getString("accountNumber",null)!=null){
            etAccountNum.setText(sp.getString("accountNumber",null));
            etConfirmAccNum.setText(sp.getString("accountNumber",null));
        }
        if(sp.getString("ifsc",null)!=null){
            etIFSC.setText(sp.getString("accountNumber",null));
        }
        if(sp.getString("accountName",null)!=null){
            etName.setText(sp.getString("accountName",null));
        }
        check();
        changeButton(v);

        etAccountNum.addTextChangedListener(new TextWatcher() {
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

        etConfirmAccNum.addTextChangedListener(new TextWatcher() {
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

        etIFSC.addTextChangedListener(new TextWatcher() {
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

        etName.addTextChangedListener(new TextWatcher() {
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


        verifyAndProceedButton.setOnClickListener(v1 -> {
            listener.showGreenTickBank();
            sp.edit().putString("accountNumber",etAccountNum.getText().toString().trim()).apply();
            sp.edit().putString("ifsc",etIFSC.getText().toString().trim()).apply();
            sp.edit().putString("accountName",etName.getText().toString().trim()).apply();
            dismiss();
        });
        return v;
    }

    public interface greenTickBank {
        void showGreenTickBank();
    }

    public void ShowGreenTickBank(greenTickBank listener) {
        this.listener = listener;
    }

    boolean check() {
        String name = etName.getText().toString().trim();
        String acc = etAccountNum.getText().toString().trim();
        String cAcc = etConfirmAccNum.getText().toString().trim();
        String ifsc = etIFSC.getText().toString().trim();
        return !name.isEmpty() && !ifsc.isEmpty() && !acc.isEmpty() && !cAcc.isEmpty() && acc.equals(cAcc) && checkBox.isChecked();
    }

    void changeButton(View v) {
        if (check()) {
            verifyAndProceedButton.setClickable(true);
            verifyAndProceedButton.setBackgroundColor(v.getContext().getColor(R.color.blue));
        } else {
            verifyAndProceedButton.setClickable(false);
            verifyAndProceedButton.setBackgroundColor(v.getContext().getColor(R.color.button_grey));
        }
    }
}
