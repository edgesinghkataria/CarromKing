package com.entwik.carromcash.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.entwik.carromcash.ApiService;
import com.entwik.carromcash.CustomProgressBar;
import com.entwik.carromcash.MyApiEndpointInterface;
import com.entwik.carromcash.MyApplication;
import com.entwik.carromcash.R;
import com.entwik.carromcash.models.paytm.PaytmRequestInitModel;
import com.entwik.carromcash.models.paytm.PaytmResponseDataModel;
import com.entwik.carromcash.models.paytm.PaytmResponseModel;
import com.entwik.carromcash.utils.ResultType;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCash_Bottomsheet extends BottomSheetDialogFragment {
    SharedPreferences sp;
    Button button10, button20, button50, button100, continueBtn;
    EditText amountEditText;
    CustomProgressBar progressBar;
    String TAG;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_cash, container, false);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, this.getClass().getSimpleName());
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, this.getClass().getSimpleName());
        MyApplication.mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);

        progressBar = new CustomProgressBar(requireActivity());
        TAG = v.getContext().getString(R.string.TAG);
        sp = v.getContext().getSharedPreferences(TAG, Context.MODE_PRIVATE);
        button10 = v.findViewById(R.id.button4);
        button20 = v.findViewById(R.id.button5);
        button50 = v.findViewById(R.id.button6);
        button100 = v.findViewById(R.id.button7);
        continueBtn = v.findViewById(R.id.continue_btn);
        amountEditText= v.findViewById(R.id.editTextNumber4);
        button10.setOnClickListener(v1 -> {
            amountEditText.setText(v.getContext().getString(R.string.rupee) + "10");
            button10.setBackgroundResource(R.drawable.custom_button);
            button20.setBackgroundResource(R.drawable.custom_button2);
            button50.setBackgroundResource(R.drawable.custom_button2);
            button100.setBackgroundResource(R.drawable.custom_button2);

            button10.setTextColor(Color.parseColor("#FFFFFF"));
            button20.setTextColor(Color.parseColor("#000000"));
            button50.setTextColor(Color.parseColor("#000000"));
            button100.setTextColor(Color.parseColor("#000000"));

        });
        button20.setOnClickListener(v12 -> {
            amountEditText.setText(v.getContext().getString(R.string.rupee) + "20");
            button20.setBackgroundResource(R.drawable.custom_button);
            button10.setBackgroundResource(R.drawable.custom_button2);
            button50.setBackgroundResource(R.drawable.custom_button2);
            button100.setBackgroundResource(R.drawable.custom_button2);

            button20.setTextColor(Color.parseColor("#FFFFFF"));
            button10.setTextColor(Color.parseColor("#000000"));
            button50.setTextColor(Color.parseColor("#000000"));
            button100.setTextColor(Color.parseColor("#000000"));
        });
        button50.setOnClickListener(v13 -> {
            amountEditText.setText(v.getContext().getString(R.string.rupee) + "50");
            button50.setBackgroundResource(R.drawable.custom_button);
            button20.setBackgroundResource(R.drawable.custom_button2);
            button10.setBackgroundResource(R.drawable.custom_button2);
            button100.setBackgroundResource(R.drawable.custom_button2);

            button50.setTextColor(Color.parseColor("#FFFFFF"));
            button20.setTextColor(Color.parseColor("#000000"));
            button10.setTextColor(Color.parseColor("#000000"));
            button100.setTextColor(Color.parseColor("#000000"));
        });
        button100.setOnClickListener(v14 -> {
            amountEditText.setText(v.getContext().getString(R.string.rupee) + "100");
            button100.setBackgroundResource(R.drawable.custom_button);
            button20.setBackgroundResource(R.drawable.custom_button2);
            button50.setBackgroundResource(R.drawable.custom_button2);
            button10.setBackgroundResource(R.drawable.custom_button2);

            button100.setTextColor(Color.parseColor("#FFFFFF"));
            button20.setTextColor(Color.parseColor("#000000"));
            button50.setTextColor(Color.parseColor("#000000"));
            button10.setTextColor(Color.parseColor("#000000"));
        });

        amountEditText.setText(v.getContext().getString(R.string.rupee));

        Selection.setSelection(amountEditText.getText(), amountEditText.getText().length());


        amountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(!s.toString().startsWith(v.getContext().getString(R.string.rupee))){
                    amountEditText.setText(v.getContext().getString(R.string.rupee));
                    Selection.setSelection(amountEditText.getText(), amountEditText.getText().length());

                }

                if(amountEditText.getText().toString().trim().equals(v.getContext().getString(R.string.rupee) + "10")) {
                    button10.setBackgroundResource(R.drawable.custom_button);
                    button20.setBackgroundResource(R.drawable.custom_button2);
                    button50.setBackgroundResource(R.drawable.custom_button2);
                    button100.setBackgroundResource(R.drawable.custom_button2);

                    button10.setTextColor(Color.parseColor("#FFFFFF"));
                    button20.setTextColor(Color.parseColor("#000000"));
                    button50.setTextColor(Color.parseColor("#000000"));
                    button100.setTextColor(Color.parseColor("#000000"));
                } else if(amountEditText.getText().toString().trim().equals(v.getContext().getString(R.string.rupee) + "20")){

                    button20.setBackgroundResource(R.drawable.custom_button);
                    button10.setBackgroundResource(R.drawable.custom_button2);
                    button50.setBackgroundResource(R.drawable.custom_button2);
                    button100.setBackgroundResource(R.drawable.custom_button2);

                    button20.setTextColor(Color.parseColor("#FFFFFF"));
                    button10.setTextColor(Color.parseColor("#000000"));
                    button50.setTextColor(Color.parseColor("#000000"));
                    button100.setTextColor(Color.parseColor("#000000"));
                }else if(amountEditText.getText().toString().trim().equals(v.getContext().getString(R.string.rupee) + "50")){
                    button50.setBackgroundResource(R.drawable.custom_button);
                    button20.setBackgroundResource(R.drawable.custom_button2);
                    button10.setBackgroundResource(R.drawable.custom_button2);
                    button100.setBackgroundResource(R.drawable.custom_button2);

                    button50.setTextColor(Color.parseColor("#FFFFFF"));
                    button20.setTextColor(Color.parseColor("#000000"));
                    button10.setTextColor(Color.parseColor("#000000"));
                    button100.setTextColor(Color.parseColor("#000000"));
                }else if(amountEditText.getText().toString().trim().equals(v.getContext().getString(R.string.rupee) + "100")){
                    button100.setBackgroundResource(R.drawable.custom_button);
                    button20.setBackgroundResource(R.drawable.custom_button2);
                    button50.setBackgroundResource(R.drawable.custom_button2);
                    button10.setBackgroundResource(R.drawable.custom_button2);

                    button100.setTextColor(Color.parseColor("#FFFFFF"));
                    button20.setTextColor(Color.parseColor("#000000"));
                    button50.setTextColor(Color.parseColor("#000000"));
                    button10.setTextColor(Color.parseColor("#000000"));
                }else{
                    button10.setBackgroundResource(R.drawable.custom_button2);
                    button20.setBackgroundResource(R.drawable.custom_button2);
                    button50.setBackgroundResource(R.drawable.custom_button2);
                    button100.setBackgroundResource(R.drawable.custom_button2);

                    button10.setTextColor(Color.parseColor("#000000"));
                    button20.setTextColor(Color.parseColor("#000000"));
                    button50.setTextColor(Color.parseColor("#000000"));
                    button100.setTextColor(Color.parseColor("#000000"));
                }
            }
        });

        continueBtn.setOnClickListener(v15 -> {
            progressBar.show();
            String amount = amountEditText.getText().toString().replace(v.getContext().getString(R.string.rupee), "").trim();
            initPayment(new PaytmRequestInitModel(amount));
        });
        return v;
    }

    private void initPayment(PaytmRequestInitModel amount) {
        ApiService apiService = new ApiService();
        MyApiEndpointInterface apiEndpointInterface = apiService.
                getApiServiceForInterceptor(apiService.getInterceptor(sp.getString("token", null)));

        apiEndpointInterface.initPaytmTxn(amount)
                .enqueue(new Callback<PaytmResponseModel>() {
                    @Override
                    public void onResponse(@NonNull Call<PaytmResponseModel> call, @NonNull Response<PaytmResponseModel> response) {
                        if (response.body() != null) {
                            if (response.body().isStatus()) {
                                PaytmResponseDataModel dataModel = response.body().getData();
                                if (dataModel != null) {
                                    progressBar.dismiss();
                                    startPayment(amount.getAmount(), dataModel.getTxnToken(), dataModel.getOrderId(), dataModel.getCallbackUrl());
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<PaytmResponseModel> call, @NonNull Throwable t) {
                        progressBar.dismiss();
                        Log.d(TAG, "onFailure: "+t.getMessage());
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && data != null) {
            Toast.makeText(requireActivity(), data.getStringExtra("nativeSdkForMerchantMessage") + data.getStringExtra("response"), Toast.LENGTH_SHORT).show();
        }
    }

    private void startPayment(String amount, String txnToken, String orderId, String callbackUrl) {
        PaytmOrder paytmOrder = new PaytmOrder(orderId, "iFsjSJ18864517452615", txnToken, amount, callbackUrl);
        TransactionManager transactionManager = new TransactionManager(paytmOrder, new PaytmPaymentTransactionCallback() {
            @Override
            public void onTransactionResponse(@Nullable Bundle response) {
                if (Objects.equals(response.getString("STATUS"), "TXN_SUCCESS")) {
                    showDepositSuccess();
                } else if (!response.getBoolean("STATUS")) {
                    showDepositFailed();
                }
            }

            @Override
            public void networkNotAvailable() {
                Log.d(TAG, "networkNotAvailable: network error");
                showDepositFailed();
            }

            @Override
            public void onErrorProceed(String s) {
                Log.d(TAG, "onErrorProceed: "+s);
                showDepositFailed();
            }

            @Override
            public void clientAuthenticationFailed(String s) {
                Log.d(TAG, "clientAuthenticationFailed: "+s);
                showDepositFailed();
            }

            @Override
            public void someUIErrorOccurred(String s) {
                Log.d(TAG, "someUIErrorOccurred: "+s);
                showDepositFailed();
            }

            @Override
            public void onErrorLoadingWebPage(int i, String s, String s1) {
                Log.d(TAG, "onErrorLoadingWebPage: "+s+" -- "+s1);
                showDepositFailed();
            }

            @Override
            public void onBackPressedCancelTransaction() {
                Log.d(TAG, "onBackButtonPressed: ");
                showDepositFailed();
            }

            @Override
            public void onTransactionCancel(String s, Bundle bundle) {
                Log.d(TAG, "onTransactionCancel: "+s);
                showDepositFailed();
            }
        });
        transactionManager.startTransaction(requireActivity(), 111);
    }

    private void showDepositFailed() {
        dismissAllowingStateLoss();
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new ResultFragment(ResultType.DEPOSIT_FAILURE)).addToBackStack(null).commitAllowingStateLoss();
    }

    private void showDepositSuccess() {
        dismissAllowingStateLoss();
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new ResultFragment(ResultType.DEPOSIT_SUCCESS)).addToBackStack(null).commitAllowingStateLoss();
    }
}
