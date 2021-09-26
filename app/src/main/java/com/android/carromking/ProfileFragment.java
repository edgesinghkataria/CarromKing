package com.android.carromking;

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

import ProfileResponseDataModel;
import ProfileResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileFragment extends Fragment {
    SharedPreferences sp;

    ProfileResponseDataModel responseModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sp = view.getContext().getSharedPreferences(getString(R.string.TAG), Context.MODE_PRIVATE);

        getProfileData();
    }

    void getProfileData() {
        Retrofit retrofit = new HeaderInterceptor().getRetrofit(sp.getString("token", null));

        MyApiEndpointInterface apiService =
                retrofit.create(MyApiEndpointInterface.class);

        apiService.getProfileData()
                .enqueue(new Callback<ProfileResponseModel>() {
                    @Override
                    public void onResponse(Call<ProfileResponseModel> call, Response<ProfileResponseModel> response) {
                        if(response.body()!=null) {
                            if(response.body().isStatus()) {
                                responseModel = response.body().getData();
                                Log.d(getString(R.string.TAG), "onResponse: " + responseModel.toString());

                            } else {
                                Toast.makeText(getContext(), response.body().getError().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ProfileResponseModel> call, Throwable t) {

                    }
                });
    }
}
