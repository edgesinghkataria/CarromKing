package com.android.carromking.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.carromking.ApiService;
import com.android.carromking.MyApiEndpointInterface;
import com.android.carromking.R;
import com.android.carromking.models.wallet.WalletResponseDataModel;
import com.android.carromking.models.wallet.WalletResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletFragment extends Fragment {

    SharedPreferences sp;
    WalletResponseDataModel dataModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wallet, container,false);

        Button addMoney = v.findViewById(R.id.button_addMoney);

        addMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCash_Bottomsheet bottomSheet = new AddCash_Bottomsheet();
                bottomSheet.show(getActivity().getSupportFragmentManager(), "AddMoney");
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sp = view.getContext().getSharedPreferences(getString(R.string.TAG), Context.MODE_PRIVATE);

        getWalletData(view.getContext());
        if(dataModel!=null) {
            ///Connect UI Here
        } else {
            //Error Handling
        }


    }

    void getWalletData(Context context) {
        ApiService apiService = new ApiService();
        MyApiEndpointInterface apiEndpointInterface = apiService.
                getApiServiceForInterceptor(apiService.getInterceptor(sp.getString("token", null)));

        apiEndpointInterface.getWalletData()
                .enqueue(new Callback<WalletResponseModel>() {
                    @Override
                    public void onResponse(@NonNull Call<WalletResponseModel> call, @NonNull Response<WalletResponseModel> response) {
                        WalletResponseModel body = response.body();
                        if(body!=null) {
                            if(body.isStatus()) {
                                dataModel = body.getData();
                                Log.d(context.getString(R.string.TAG), "Wallet onResponse: " + body.getData().getUserId());
                            } else {
                                Toast.makeText(getContext(), body.getError().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<WalletResponseModel> call, @NonNull Throwable t) {

                    }
                });
    }
}
