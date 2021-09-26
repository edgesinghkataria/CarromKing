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
import com.android.carromking.models.profile.ProfileResponseDataModel;
import com.android.carromking.models.profile.ProfileResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileFragment extends Fragment {
    SharedPreferences sp;

    ProfileResponseDataModel dataModel;

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
        if(dataModel!=null) {
            ///Connect UI Here
        } else {
            //Error Handling
        }
    }

    void getProfileData() {
        HeaderInterceptor interceptor = new HeaderInterceptor();
        Retrofit retrofit = interceptor.getRetrofit(interceptor.getInterceptor(sp.getString("token", null)));

        MyApiEndpointInterface apiService =
                retrofit.create(MyApiEndpointInterface.class);

        apiService.getProfileData()
                .enqueue(new Callback<ProfileResponseModel>() {
                    @Override
                    public void onResponse(Call<ProfileResponseModel> call, Response<ProfileResponseModel> response) {
                        if(response.body()!=null) {
                            if(response.body().isStatus()) {
                                dataModel = response.body().getData();
                                Log.d(getString(R.string.TAG), "onResponse: Profile " + dataModel.getUserData().getUsername());

                                /*
                                {dataModel.getUserData().getMobileNumber()}
                                and
                                {dataModel.getUserData().getToken()}
                                is null here
                                Don't use it for this api
                                 */

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
