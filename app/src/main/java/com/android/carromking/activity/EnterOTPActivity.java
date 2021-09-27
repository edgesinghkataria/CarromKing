package com.android.carromking.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.carromking.ApiService;
import com.android.carromking.CustomProgressBar;
import com.android.carromking.MyApiEndpointInterface;
import com.android.carromking.R;
import com.android.carromking.models.common.UserDataModel;
import com.android.carromking.models.common.UserWalletDataModel;
import com.android.carromking.models.local.LocalDataModel;
import com.android.carromking.models.otp.VerifyOTPBodyModel;
import com.android.carromking.models.otp.VerifyOTPResponseDataModel;
import com.android.carromking.models.otp.VerifyOTPResponseModel;
import com.android.carromking.models.wallet.WalletResponseDataModel;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnterOTPActivity extends AppCompatActivity {

    TextView tvMobileNumber;
    EditText etOTP;
    Button btnVerify;

    ApiService apiService = new ApiService();
    MyApiEndpointInterface apiEndpointInterface = apiService.getApiService();

    CustomProgressBar progressBar;

    String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otpactivity);
        getSupportActionBar().hide();
        progressBar = new CustomProgressBar(this);

        TAG = getString(R.string.TAG);

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
            progressBar.show();
            String otpText = etOTP.getText().toString().trim();

            if(otpText.length() == 6) {
                apiEndpointInterface.verifyOTP(new VerifyOTPBodyModel(mobileNumber, sessionId, otpText))
                        .enqueue(new Callback<VerifyOTPResponseModel>() {
                            @Override
                            public void onResponse(@NonNull Call<VerifyOTPResponseModel> call, @NonNull Response<VerifyOTPResponseModel> response) {
                                if(response.body()!=null) {
                                    if(!response.body().isStatus()) {
                                        Toast.makeText(EnterOTPActivity.this, response.body().getError().getMessage(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        sp.edit().putString("token", response.body().getData().getUserData().getToken()).apply();
                                        sp.edit().putString("sessionId", sessionId).apply();
                                        storeDataInLocal(response.body().getData(), sp, numberWithCode);
                                        progressBar.hide();
                                        Intent i = new Intent(EnterOTPActivity.this, MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<VerifyOTPResponseModel> call, @NonNull Throwable t) {

                            }
                        });
            }
        });


    }

    void storeDataInLocal(VerifyOTPResponseDataModel dataModel, SharedPreferences sp, String mobileNumber) {
        UserDataModel userData = dataModel.getUserData();
        UserWalletDataModel walletData = dataModel.getUserWalletData();
        LocalDataModel localDataModel = new LocalDataModel(
                String.valueOf(userData.getId()),
                mobileNumber,
                userData.getProfilePic(),
                userData.getLevel(),
                userData.getToken(),
                String.valueOf(walletData.getWinningBalance()),
                String.valueOf(walletData.getDepositBalance()),
                String.valueOf(walletData.getBonusBalance())

        );

        Gson gson = new Gson();
        String json = gson.toJson(localDataModel);
        sp.edit().putString("local", json).apply();
    }
}