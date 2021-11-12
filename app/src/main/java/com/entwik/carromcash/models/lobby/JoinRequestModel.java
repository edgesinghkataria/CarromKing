package com.entwik.carromcash.models.lobby;

public class JoinRequestModel {
    private int lobbyId;

    public JoinRequestModel(int lobbyId) {
        this.lobbyId = lobbyId;
    }

    public int getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(int lobbyId) {
        this.lobbyId = lobbyId;
    }
}
