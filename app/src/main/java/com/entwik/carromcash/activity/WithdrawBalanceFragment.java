package com.entwik.carromcash.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.Html;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.entwik.carromcash.ApiService;
import com.entwik.carromcash.CustomProgressBar;
import com.entwik.carromcash.MyApiEndpointInterface;
import com.entwik.carromcash.MyApplication;
import com.entwik.carromcash.R;
import com.entwik.carromcash.models.account.AccountDetailsModel;
import com.entwik.carromcash.models.account.AccountResponseModel;
import com.entwik.carromcash.models.debit.DebitInitRequestModel;
import com.entwik.carromcash.models.debit.DebitResponseModel;
import com.entwik.carromcash.models.local.LocalDataModel;
import com.entwik.carromcash.models.paytm.PaytmResponseDataModel;
import com.entwik.carromcash.utils.ResultType;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WithdrawBalanceFragment extends Fragment {

    final Gson gson = new Gson();
    SharedPreferences sp;
    String TAG;
    boolean isUpi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_withdraw_balance, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, this.getClass().getSimpleName());
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, this.getClass().getSimpleName());
        MyApplication.mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);

        TAG = getString(R.string.TAG);
        sp = view.getContext().getSharedPreferences(TAG, Context.MODE_PRIVATE);

        LocalDataModel localDataModel1 =  new LocalDataModel(
                "1",
                getString(R.string.mobile_number),
                "",
                "silver",
                false,
                sp.getString("token", null),
                "0",
                "0",
                "0"
        );

        LocalDataModel localDataModel = gson.fromJson(sp.getString("local", gson.toJson(localDataModel1)), LocalDataModel.class);


        Button withdrawNow = view.findViewById(R.id.withdraw_now_button);
//        TextView linkPaytm = view.findViewById(R.id.link_account_paytm);
        LinearLayout linkUPI = view.findViewById(R.id.arrow_upi);
        LinearLayout linkBank = view.findViewById(R.id.arrow_bank_transfer);
//        LinearLayout greenTickAndArrowPaytm = view.findViewById(R.id.greenTickAndArrowPaytm);
        ImageView greenTickUpi = view.findViewById(R.id.greenTickUpi);
        ImageView greenTickBank = view.findViewById(R.id.greenTickBankTransfer);
        TextView withdrawableBalance = view.findViewById(R.id.withdrawble_balance_amount);
        EditText etAmountWithdraw = view.findViewById(R.id.amount_to_withdraw);
        CustomProgressBar progressBar = new CustomProgressBar(getActivity());

        withdrawableBalance.setText( "₹ "+ localDataModel.getWinningBalance());

        withdrawNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.show();
                String amount = etAmountWithdraw.getText().toString().substring(1);

                ApiService apiService = new ApiService();
                MyApiEndpointInterface apiEndpointInterface = apiService.
                        getApiServiceForInterceptor(apiService.getInterceptor(sp.getString("token", null)));
                postAccountDetails();

                apiEndpointInterface.initDebit(new DebitInitRequestModel(amount))
                        .enqueue(new Callback<DebitResponseModel>() {
                            @Override
                            public void onResponse(Call<DebitResponseModel> call, Response<DebitResponseModel> response) {
                                progressBar.dismiss();
                                if (response.body() != null) {
                                    if (response.body().isStatus()) {
                                        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                                new ResultFragment(ResultType.WITHDRAWAL_SUCCESS)).addToBackStack(null).commit();
                                    }
                                } else {
                                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                            new ResultFragment(ResultType.WITHDRAWAL_FAILURE)).addToBackStack(null).commit();
                                }
                            }

                            @Override
                            public void onFailure(Call<DebitResponseModel> call, Throwable t) {
                                progressBar.dismiss();
                                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                        new ResultFragment(ResultType.WITHDRAWAL_FAILURE)).addToBackStack(null).commit();
                            }
                        });
            }
        });

//        linkPaytm.setOnClickListener(v -> {
//            LinkPaytm_Bottomsheet bottomSheet = new LinkPaytm_Bottomsheet();
//            bottomSheet.ShowGreenTickPaytmTwo(() -> {
//                greenTickAndArrowPaytm.setVisibility(View.VISIBLE);
//                linkPaytm.setVisibility(View.GONE);
//                greenTickBank.setVisibility(View.GONE);
//                greenTickUpi.setVisibility(View.GONE);
//            });
//            bottomSheet.show(requireActivity().getSupportFragmentManager(), "LinkPaytm");
//        });

        linkUPI.setOnClickListener(v -> {
            isUpi=true;
            EnterUPI_Bottomsheet bottomSheet = new EnterUPI_Bottomsheet();
            bottomSheet.ShowGreenTickUpi(() -> {
                greenTickUpi.setVisibility(View.VISIBLE);
                greenTickBank.setVisibility(View.GONE);
//                greenTickAndArrowPaytm.setVisibility(View.GONE);
//                linkPaytm.setVisibility(View.VISIBLE);
            });
            bottomSheet.show(requireActivity().getSupportFragmentManager(), "LinkUPI");
        });

        linkBank.setOnClickListener(v -> {
            isUpi=false;
            LinkBank_Bottomsheet bottomSheet = new LinkBank_Bottomsheet();
            bottomSheet.ShowGreenTickBank(() -> {
                greenTickBank.setVisibility(View.VISIBLE);
                greenTickUpi.setVisibility(View.GONE);
//                linkPaytm.setVisibility(View.VISIBLE);
//                greenTickAndArrowPaytm.setVisibility(View.GONE);
            });
            bottomSheet.show(requireActivity().getSupportFragmentManager(), "LinkBank");
        });

        Selection.setSelection(etAmountWithdraw.getText(), etAmountWithdraw.getText().length());

        etAmountWithdraw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().startsWith(view.getContext().getString(R.string.rupee))){
                    etAmountWithdraw.setText(view.getContext().getString(R.string.rupee));
                    Selection.setSelection(etAmountWithdraw.getText(), etAmountWithdraw.getText().length());

                }
            }
        });


    }


    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)requireActivity()).getSupportActionBar().show();
        ((AppCompatActivity)requireActivity()).getSupportActionBar().setTitle(Html.fromHtml("<font color=\"black\">" + "Withdraw Balance" + "</font>"));
        ((AppCompatActivity)requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)requireActivity()).getSupportActionBar().setElevation(0);
        ((AppCompatActivity)requireActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        @SuppressLint("UseCompatLoadingForDrawables")
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_keyboard_backspace_24, requireContext().getTheme());
        ((AppCompatActivity)requireActivity()).getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)requireActivity()).getSupportActionBar().hide();
    }
    private void postAccountDetails(){
        String upi = null,accountNumber=null,ifsc=null,accountName=null;
        if(isUpi){
            upi = sp.getString("upi",null);
        }else{
            accountNumber = sp.getString("accountNumber",null);
            ifsc = sp.getString("ifsc",null);
            accountName = sp.getString("accountName",null);
        }

        ApiService interceptor = new ApiService();
        MyApiEndpointInterface apiEndpointInterface = interceptor.getApiServiceForInterceptor(interceptor.getInterceptor(sp.getString("token", null)));
        AccountDetailsModel accountDetailsModel = new AccountDetailsModel(upi,accountNumber,"icici",ifsc);

        apiEndpointInterface.accountDetails(accountDetailsModel)
                .enqueue(new Callback<AccountResponseModel>() {
                    @Override
                    public void onResponse(Call<AccountResponseModel> call, Response<AccountResponseModel> response) {
                        if(response.isSuccessful()){
                            //ToDo: What to do on success
//                            Toast.makeText(getActivity(), "UPI Id saved", Toast.LENGTH_SHORT).show();
                        }else{

//                            Toast.makeText(getContext(), "Unable to save UPI Id\nPlease try again later",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AccountResponseModel> call, Throwable t) {

                    }
                });


    }
}
