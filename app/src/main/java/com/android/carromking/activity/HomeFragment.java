package com.android.carromking.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.carromking.ApiService;
import com.android.carromking.CustomProgressBar;
import com.android.carromking.MyApiEndpointInterface;
import com.android.carromking.R;
import com.android.carromking.models.home.HomeResponseDataModel;
import com.android.carromking.models.home.HomeResponseModel;
import com.android.carromking.models.home.LobbyModel;
import com.android.carromking.models.local.LocalDataModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {


    SharedPreferences sp;
    HomeResponseDataModel dataModel;
    home_list_adapter adapter;
    RecyclerView HomeRecyclerView;
    TextView beginner, silver, gold, diamond, homeWallet;
    List<LobbyModel> lobbyList, beginnerList, silverList, goldList, diamondList;
    LocalDataModel localDataModel;

    CustomProgressBar progressBar;
    final Gson gson = new Gson();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container,false);

        homeWallet = v.findViewById(R.id.home_wallet);
        beginner = v.findViewById(R.id.home_beginner);
        silver = v.findViewById(R.id.home_silver);
        gold = v.findViewById(R.id.home_gold);
        diamond = v.findViewById(R.id.home_diamond);

        progressBar = new CustomProgressBar(getActivity());

        HomeRecyclerView = v.findViewById(R.id.home_RecyclerView);
        HomeRecyclerView.setHasFixedSize(true);
        HomeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new home_list_adapter();

        beginner.setOnClickListener(v14 -> {
            beginner.setTypeface(null, Typeface.BOLD);
            silver.setTypeface(null, Typeface.NORMAL);
            gold.setTypeface(null, Typeface.NORMAL);
            diamond.setTypeface(null, Typeface.NORMAL);
            adapter.setTasks(beginnerList);
            HomeRecyclerView.setAdapter(adapter);
        });

        silver.setOnClickListener(v13 -> {
            silver.setTypeface(null, Typeface.BOLD);
            beginner.setTypeface(null, Typeface.NORMAL);
            gold.setTypeface(null, Typeface.NORMAL);
            diamond.setTypeface(null, Typeface.NORMAL);
            adapter.setTasks(silverList);
            HomeRecyclerView.setAdapter(adapter);
        });

        gold.setOnClickListener(v12 -> {
            gold.setTypeface(null, Typeface.BOLD);
            silver.setTypeface(null, Typeface.NORMAL);
            beginner.setTypeface(null, Typeface.NORMAL);
            diamond.setTypeface(null, Typeface.NORMAL);
            adapter.setTasks(goldList);
            HomeRecyclerView.setAdapter(adapter);
        });

        diamond.setOnClickListener(v1 -> {
            diamond.setTypeface(null, Typeface.BOLD);
            silver.setTypeface(null, Typeface.NORMAL);
            gold.setTypeface(null, Typeface.NORMAL);
            beginner.setTypeface(null, Typeface.NORMAL);
            adapter.setTasks(diamondList);
            HomeRecyclerView.setAdapter(adapter);
        });

        sp = v.getContext().getSharedPreferences(getString(R.string.TAG), Context.MODE_PRIVATE);

        LocalDataModel localDataModel1 =  new LocalDataModel(
                "1",
                getString(R.string.mobile_number),
                "",
                "silver",
                sp.getString("token", null),
                "0",
                "0",
                "0"
        );
        localDataModel = gson.fromJson(sp.getString("local", gson.toJson(localDataModel1)), LocalDataModel.class);
        String unPlayedBalance = localDataModel.getDepositBalance();
        String winningBalance = localDataModel.getWinningBalance();
        String bonusBalance = localDataModel.getBonusBalance();

        homeWallet.setText(String.valueOf(Integer.parseInt(unPlayedBalance) + Integer.parseInt(winningBalance) + Integer.parseInt(bonusBalance)));

        switch (localDataModel.getLevel()) {
            case "beginner":
                beginner.setTypeface(null, Typeface.BOLD);
                break;
            case "silver":
                silver.setTypeface(null, Typeface.BOLD);
                break;
            case "gold":
                gold.setTypeface(null, Typeface.BOLD);
                break;
            case "diamond":
                diamond.setTypeface(null, Typeface.BOLD);
                break;
        }

        return v;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getHomeData();

    }

    private void getHomeData() {

        progressBar.show();

        ApiService interceptor = new ApiService();
        MyApiEndpointInterface apiEndpointInterface = interceptor.getApiServiceForInterceptor(interceptor.getInterceptor(sp.getString("token", null)));

//        if(!interceptor.internetIsConnected()) {
//            progressBar.hide();
//            Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT).show();
//        } else {
            apiEndpointInterface.getHomeData()
                    .enqueue(new Callback<HomeResponseModel>() {
                        @Override
                        public void onResponse(@NonNull Call<HomeResponseModel> call, @NonNull Response<HomeResponseModel> response) {
                            if (response.body() != null) {
                                if (response.body().isStatus()) {
                                    dataModel = response.body().getData().get(0);
                                    if (dataModel != null) {
                                        beginnerList = new ArrayList<>();
                                        silverList = new ArrayList<>();
                                        goldList = new ArrayList<>();
                                        diamondList = new ArrayList<>();
                                        lobbyList = dataModel.getLobbies();

                                        for (LobbyModel lobby : lobbyList) {
                                            switch (lobby.getLevel()) {
                                                case "beginner":
                                                    beginnerList.add(lobby);
                                                    break;
                                                case "silver":
                                                    silverList.add(lobby);
                                                    break;
                                                case "gold":
                                                    goldList.add(lobby);
                                                    break;
                                                case "diamond":
                                                    diamondList.add(lobby);
                                                    break;
                                            }
                                        }
                                        switch (localDataModel.getLevel()) {
                                            case "beginner":
                                                adapter.setTasks(beginnerList);
                                                break;
                                            case "silver":
                                                adapter.setTasks(silverList);
                                                break;
                                            case "gold":
                                                adapter.setTasks(goldList);
                                                break;
                                            case "diamond":
                                                adapter.setTasks(diamondList);
                                                break;
                                        }
                                        //adapter.setTasks(lobbyList);
                                        HomeRecyclerView.setAdapter(adapter);
                                        progressBar.dismiss();
                                    } else {
                                        progressBar.dismiss();
                                        Toast.makeText(requireContext(), "Unable to refresh, try again later", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    progressBar.dismiss();
                                    Toast.makeText(getContext(), response.body().getError().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<HomeResponseModel> call, @NonNull Throwable t) {
                            progressBar.dismiss();
                        }
                    });
//        }
    }


}
