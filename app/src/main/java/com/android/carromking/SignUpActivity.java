package com.android.carromking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

public class SignUpActivity extends AppCompatActivity {
    
    CountryCodePicker ccp;
    EditText etPhone;
    Button btnGetOTP;

    final String TAG = "com.android.carromking";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        
        ccp = findViewById(R.id.ccp);
        etPhone = findViewById(R.id.etPhone);
        btnGetOTP = findViewById(R.id.btnGetOTP);

        btnGetOTP.setClickable(false);
        ccp.registerCarrierNumberEditText(etPhone);

        Log.d(TAG, "onCreate: " + ccp.isValidFullNumber());

        etPhone.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        Log.d(TAG, "onTextChanged: " + ccp.isValidFullNumber());
                        if(ccp.isValidFullNumber()) {
                            btnGetOTP.setBackgroundColor(getResources().getColor(R.color.blue));
                            btnGetOTP.setClickable(true);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                }
        );


        btnGetOTP.setOnClickListener(view -> {
            String ccpText = ccp.toString();
            String phoneText = etPhone.getText().toString().trim();

            
            
            if(!ccp.isValidFullNumber() || phoneText.isEmpty()) {
                Toast.makeText(this, "Please add a valid mobile number", Toast.LENGTH_SHORT).show();
            }
        });
    }
}