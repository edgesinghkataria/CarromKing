package com.android.carromking.models.local;


import com.android.carromking.models.home.LobbyModel;

import java.util.List;
import java.util.Map;

public interface OnLeagueDataChangeListener {
    void onDataChange(Map<String, List<LobbyModel>> lobbies);
}
