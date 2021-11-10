package com.entwik.carromcash.models.lobby;

import com.entwik.carromcash.models.wallet.WalletResponseDataModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JoinResponseDataModel {
    @SerializedName("deficitAmount")
    @Expose
    private int deficitAmount;
    @SerializedName("wallet")
    @Expose
    private WalletResponseDataModel wallet;

    public int getDeficitAmount() {
        return deficitAmount;
    }

    public void setDeficitAmount(int deficitAmount) {
        this.deficitAmount = deficitAmount;
    }

    public WalletResponseDataModel getWallet() {
        return wallet;
    }

    public void setWallet(WalletResponseDataModel wallet) {
        this.wallet = wallet;
    }
}
