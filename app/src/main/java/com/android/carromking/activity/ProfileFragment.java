package com.android.carromking.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.carromking.ApiService;
import com.android.carromking.MyApiEndpointInterface;
import com.android.carromking.R;
import com.android.carromking.models.common.UserDataModel;
import com.android.carromking.models.profile.LeagueModel;
import com.android.carromking.models.profile.ProfileResponseDataModel;
import com.android.carromking.models.profile.ProfileResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    SharedPreferences sp;
    ProfileResponseDataModel dataModel;

    TextView tvMobileNumber, tvAmountBalance;
    ImageView profile_lock_diamond, profile_lock_gold, profile_lock_silver;
    TextView profile_status_gold, profile_status_diamond, profile_status_silver, profile_user_league_name;

    ImageView profileImage, trophyImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sp = view.getContext().getSharedPreferences(getString(R.string.TAG), Context.MODE_PRIVATE);
        Log.d(getString(R.string.TAG), "onViewCreated: " + sp.getString("token", null));

        tvMobileNumber = view.findViewById(R.id.profile_phone_num);
        tvAmountBalance = view.findViewById(R.id.tvBalance);
        profile_lock_diamond = view.findViewById(R.id.profile_lock_diamond);
        profile_lock_gold = view.findViewById(R.id.profile_lock_gold);
        profile_lock_silver = view.findViewById(R.id.profile_lock_silver);
        profile_status_gold = view.findViewById(R.id.profile_status_gold);
        profile_status_diamond = view.findViewById(R.id.profile_status_diamond);
        profile_status_silver = view.findViewById(R.id.profile_status_silver);
        profile_user_league_name = view.findViewById(R.id.profile_user_league_name);

        trophyImage = view.findViewById(R.id.profile_user_league_img);
        profileImage = view.findViewById(R.id.profile_profile_icon);

        new MyTask().execute(view);
    }

    private class MyTask extends AsyncTask<View, Integer, ProfileResponseDataModel> {

        @Override
        protected ProfileResponseDataModel doInBackground(View... views) {

            ApiService apiService = new ApiService();
            MyApiEndpointInterface apiEndpointInterface = apiService.getApiServiceForInterceptor(apiService.getInterceptor(sp.getString("token", null)));
            apiEndpointInterface.getProfileData()
                    .enqueue(new Callback<ProfileResponseModel>() {
                        @Override
                        public void onResponse(@NonNull Call<ProfileResponseModel> call, @NonNull Response<ProfileResponseModel> response) {
                            if (response.body() != null) {
                                if (response.body().isStatus()) {
                                    dataModel = response.body().getData();
                                    Log.d(views[0].getContext().getString(R.string.TAG), "onResponse: Profile " + dataModel.getUserData().getUsername());

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
                        public void onFailure(@NonNull Call<ProfileResponseModel> call, @NonNull Throwable t) {

                        }
                    });
            while (true) {
                if(dataModel!=null) {
                    return dataModel;
                }
            }
        }

        @Override
        protected void onPostExecute(ProfileResponseDataModel dataModel) {
            if (dataModel != null) {
                //Connect UI Here
                UserDataModel user = dataModel.getUserData();
                List<LeagueModel> leagues = dataModel.getLeagues();
                tvMobileNumber.setText(user.getUsername());
                tvAmountBalance.setText(String.valueOf(dataModel.getWalletData().getDepositBalance()));

                switch (user.getLevel()) {
                    case "diamond":
                        trophyImage.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.diamond_trophy_tilted));
                        profile_user_league_name.setText(requireContext().getString(R.string.diamond_lea));
                        break;
                    case "gold":
                        trophyImage.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.golden_trophy_tilted));
                        profile_user_league_name.setText(requireContext().getString(R.string.gold_lea));
                        break;
                    case "silver":
                        trophyImage.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.silver_trophy_tilted));
                        profile_user_league_name.setText(requireContext().getString(R.string.silver_lea));
                        break;
                }

                trophyImage.setRotation(12);



                leagues.forEach(league -> {
                    switch (league.getName()) {
                        case "diamond":
                            profile_lock_diamond.setVisibility(league.isIsLocked() ? View.VISIBLE : View.GONE);
                            profile_status_diamond.setText(league.getDescription());
                            break;
                        case "gold":
                            profile_lock_gold.setVisibility(league.isIsLocked() ? View.VISIBLE : View.GONE);
                            profile_status_gold.setText(league.getDescription());
                            break;
                        case "silver":
                            profile_lock_silver.setVisibility(league.isIsLocked() ? View.VISIBLE : View.GONE);
                            profile_status_silver.setText(league.getDescription());
                            break;
                    }
                });
            } else {
                Toast.makeText(requireContext(), "Unable to refresh, try again later", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
