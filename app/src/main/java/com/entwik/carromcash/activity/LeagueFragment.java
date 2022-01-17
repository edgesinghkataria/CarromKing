package com.entwik.carromcash.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.entwik.carromcash.ApiService;
import com.entwik.carromcash.CustomProgressBar;
import com.entwik.carromcash.MyApiEndpointInterface;
import com.entwik.carromcash.MyApplication;
import com.entwik.carromcash.R;
import com.entwik.carromcash.models.common.ResponseErrorModel;
import com.entwik.carromcash.models.home.LobbyModel;
import com.entwik.carromcash.models.lobby.JoinRequestModel;
import com.entwik.carromcash.models.lobby.JoinResponseDataModel;
import com.entwik.carromcash.models.lobby.JoinResponseModel;
import com.entwik.carromcash.models.local.LeagueList;
import com.entwik.carromcash.models.local.OnLeagueDataChangeListener;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LeagueFragment extends Fragment implements OnLeagueDataChangeListener{

    String lobbyType;
    List<LobbyModel> mLobbies;

    public LeagueFragment(String lobbyType) {
        super();
        this.lobbyType = lobbyType;
    }

    home_list_adapter adapter;
    SharedPreferences sp;
    CustomProgressBar progressBar;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_league, container, false);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, lobbyType);
        MyApplication.mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);

        recyclerView = view.findViewById(R.id.home_RecyclerView);
        sp = view.getContext().getSharedPreferences(getString(R.string.TAG), Context.MODE_PRIVATE);
//        recyclerView.setHasFixedSize(true);
        progressBar = new CustomProgressBar(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new home_list_adapter(new HomeOnClickListener() {
            @Override
            public void onClick(int position) {
                progressBar.show();
                checkBalanceAndLaunch(position);
            }
        });

        LeagueList.getInstance().addLeagueDataChangeListener(this);

        return view;
    }

    private void checkBalanceAndLaunch(int position) {
        LobbyModel lobby = mLobbies.get(position);
        int id = lobby.getId();

        ApiService apiService = new ApiService();
        MyApiEndpointInterface apiEndpointInterface = apiService.
                getApiServiceForInterceptor(apiService.getInterceptor(sp.getString("token", null)));

        apiEndpointInterface.joinLobby(new JoinRequestModel(id))
                .enqueue(new Callback<JoinResponseModel>() {
                    @Override
                    public void onResponse(Call<JoinResponseModel> call, Response<JoinResponseModel> response) {
                        if (response.body() != null && response.body().isStatus()) {
                                JoinResponseDataModel dataModel = response.body().getData();
                                if (dataModel != null) {
                                    progressBar.dismiss();
                                    if (dataModel.getDeficitAmount() > 0) {
                                        new AddMoneyDialog().show(requireActivity().getSupportFragmentManager(), "add money");
                                    } else {
                                        // Launch game
                                        Toast.makeText(getActivity(), "Game Launched", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        else{
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                double deficitAmount = jObjError.getJSONObject("error").getJSONObject("data").getDouble("deficitAmount");
                                if(deficitAmount>0){
                                    new AddMoneyDialog().show(requireActivity().getSupportFragmentManager(), "add money");
                                }
                            } catch (Exception e) {
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            progressBar.dismiss();

                        }
                    }

                    @Override
                    public void onFailure(Call<JoinResponseModel> call, Throwable t) {

                    }
                });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LeagueList.getInstance().removeLeagueDataChangeListener(this);
    }

    @Override
    public void onDataChange(Map<String, List<LobbyModel>> lobbies) {
        mLobbies = lobbies.get(lobbyType);
        adapter.setTasks(lobbies.get(lobbyType));
        recyclerView.setAdapter(adapter);
    }
}