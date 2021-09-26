package com.android.carromking.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.carromking.HeaderInterceptor;
import com.android.carromking.MyApiEndpointInterface;
import com.android.carromking.R;
import com.android.carromking.models.wallet.WalletResponseDataModel;
import com.android.carromking.models.wallet.WalletResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WalletFragment extends Fragment {

    SharedPreferences sp;
    WalletResponseDataModel dataModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallet, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sp = view.getContext().getSharedPreferences(getString(R.string.TAG), Context.MODE_PRIVATE);

        getWalletData();
        if(dataModel!=null) {
            ///Connect UI Here
        } else {
            //Error Handling
        }


    }

    void getWalletData() {
        HeaderInterceptor interceptor = new HeaderInterceptor();

        Retrofit retrofit = interceptor.getRetrofit(interceptor.getInterceptor(sp.getString("token", null)));

        MyApiEndpointInterface apiEndpointInterface = retrofit.create(MyApiEndpointInterface.class);
        apiEndpointInterface.getWalletData()
                .enqueue(new Callback<WalletResponseModel>() {
                    @Override
                    public void onResponse(@NonNull Call<WalletResponseModel> call, Response<WalletResponseModel> response) {
                        WalletResponseModel body = response.body();
                        if(body!=null) {
                            if(body.isStatus()) {
                                dataModel = body.getData();
                                Log.d(getString(R.string.TAG), "Wallet onResponse: " + body.getData().getUserId());
                            } else {
                                Toast.makeText(getContext(), body.getError().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<WalletResponseModel> call, Throwable t) {

                    }
                });
    }
}
