package com.android.carromking.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.android.carromking.models.home.HomeResponseDataModel;
import com.android.carromking.models.home.HomeResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {

    SharedPreferences sp;
    HomeResponseDataModel dataModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container,false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sp = view.getContext().getSharedPreferences(getString(R.string.TAG), Context.MODE_PRIVATE);
        getHomeData();
        if(dataModel!=null) {
            ///Connect UI Here
        } else {
            //Error Handling
        }

    }

    void getHomeData() {
        HeaderInterceptor interceptor = new HeaderInterceptor();
        Retrofit retrofit = interceptor.getRetrofit(interceptor.getInterceptor(sp.getString("token", null)));

        MyApiEndpointInterface apiEndpointInterface = retrofit.create(MyApiEndpointInterface.class);

        apiEndpointInterface.getHomeData()
                .enqueue(new Callback<HomeResponseModel>() {
                    @Override
                    public void onResponse(@NonNull Call<HomeResponseModel> call, Response<HomeResponseModel> response) {
                        if(response.body()!=null) {
                            if(response.body().isStatus()) {
                                dataModel = response.body().getData().get(0);
                            } else {
                                Toast.makeText(getContext(), response.body().getError().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<HomeResponseModel> call, Throwable t) {

                    }
                });
    }
}
