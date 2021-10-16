package com.android.carromking.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.carromking.CustomProgressBar;
import com.android.carromking.R;
import com.android.carromking.models.home.LobbyModel;
import com.android.carromking.models.local.LeagueList;
import com.android.carromking.models.local.OnLeagueDataChangeListener;

import java.util.List;
import java.util.Map;


public class LeagueFragment extends Fragment implements OnLeagueDataChangeListener{

    String lobbyType;

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

        recyclerView = view.findViewById(R.id.home_RecyclerView);
        sp = view.getContext().getSharedPreferences(getString(R.string.TAG), Context.MODE_PRIVATE);
        recyclerView.setHasFixedSize(true);
        progressBar = new CustomProgressBar(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new home_list_adapter();

        LeagueList.getInstance().addLeagueDataChangeListener(this);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LeagueList.getInstance().removeLeagueDataChangeListener(this);
    }

    @Override
    public void onDataChange(Map<String, List<LobbyModel>> lobbies) {
        adapter.setTasks(lobbies.get(lobbyType));
        recyclerView.setAdapter(adapter);
    }
}