package com.android.carromking.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
    TextView beginner, silver, gold, diamond;
    List<LobbyModel> lobbyList;

    CustomProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container,false);

        beginner = v.findViewById(R.id.home_beginner);
        silver = v.findViewById(R.id.home_silver);
        gold = v.findViewById(R.id.home_gold);
        diamond = v.findViewById(R.id.home_diamond);

        progressBar = new CustomProgressBar(getActivity());

        HomeRecyclerView = v.findViewById(R.id.home_RecyclerView);
        HomeRecyclerView.setHasFixedSize(true);
        HomeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new home_list_adapter();
        return v;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sp = view.getContext().getSharedPreferences(getString(R.string.TAG), Context.MODE_PRIVATE);

        progressBar.show();
        new getHome().execute(view);

    }

    private class getHome extends AsyncTask<View, Integer, HomeResponseDataModel> {

        @Override
        protected HomeResponseDataModel doInBackground(View... views) {
            Context context = views[0].getContext();

            ApiService interceptor = new ApiService();
            MyApiEndpointInterface apiEndpointInterface = interceptor.getApiServiceForInterceptor(interceptor.getInterceptor(sp.getString("token", null)));

            apiEndpointInterface.getHomeData()
                    .enqueue(new Callback<HomeResponseModel>() {
                        @Override
                        public void onResponse(@NonNull Call<HomeResponseModel> call, @NonNull Response<HomeResponseModel> response) {
                            if (response.body() != null) {
                                if (response.body().isStatus()) {
                                    dataModel = response.body().getData().get(0);
                                    progressBar.hide();
                                } else {
                                    progressBar.hide();
                                    Toast.makeText(getContext(), response.body().getError().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<HomeResponseModel> call, @NonNull Throwable t) {
                            progressBar.hide();
                        }
                    });

            while (true) {
                if(dataModel!=null) {
                    return dataModel;
                }
            }
        }

        @Override
        protected void onPostExecute(HomeResponseDataModel dataModel1) {
            if(dataModel1!=null) {
                //lobbyList = new ArrayList<>();
                lobbyList = dataModel1.getLobbies();
                adapter.setTasks(lobbyList);
                HomeRecyclerView.setAdapter(adapter);
            }

        }
    }


}
