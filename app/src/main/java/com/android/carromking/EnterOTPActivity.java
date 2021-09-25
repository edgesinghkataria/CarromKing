package com.android.carromking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EnterOTPActivity extends AppCompatActivity {

    TextView tvMobileNumber;
    EditText etOTP;
    Button btnVerify;

    final String TAG = "com.android.carromking";

    public static final String BASE_URL = "https://ecommerce-checkout.herokuapp.com/";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    MyApiEndpointInterface apiService =
            retrofit.create(MyApiEndpointInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otpactivity);

        tvMobileNumber = findViewById(R.id.tvMobileNumber);
        etOTP = findViewById(R.id.etOTP);
        btnVerify = findViewById(R.id.btnVerify);

        btnVerify.setClickable(false);

        SharedPreferences sp = getSharedPreferences(TAG, MODE_PRIVATE);
        String numberWithCode = sp.getString("mobileNumber", "+91 9999999999");

        tvMobileNumber.setText(numberWithCode);

        Bundle bundle = getIntent().getExtras();
        String mobileNumber = bundle.getString("mobileNumber");
        String sessionId = bundle.getString("sessionId");


        etOTP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(etOTP.getText().toString().trim().length() == 6) {
                    btnVerify.setClickable(true);
                    btnVerify.setBackgroundColor(getColor(R.color.blue));
                } else {
                    btnVerify.setClickable(false);
                    btnVerify.setBackgroundColor(getColor(R.color.button_grey));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnVerify.setOnClickListener(view -> {
            String otpText = etOTP.getText().toString().trim();

            if(otpText.length() == 6) {
                apiService.verifyOTP(new VerifyOTPBodyModel(mobileNumber, sessionId, otpText))
                        .enqueue(new Callback<VerifyOTPResponseModel>() {
                            @Override
                            public void onResponse(@NonNull Call<VerifyOTPResponseModel> call, Response<VerifyOTPResponseModel> response) {
                                if(response.body()!=null) {
                                    if(!response.body().isStatus()) {
                                        Toast.makeText(EnterOTPActivity.this, response.body().getError().getMessage(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        sp.edit().putString("token", response.body().getData().getUserData().getToken()).apply();
                                        sp.edit().putString("sessionId", sessionId).apply();                                        Intent i = new Intent(EnterOTPActivity.this, MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<VerifyOTPResponseModel> call, Throwable t) {

                            }
                        });
            }
        });


    }
}