package com.android.carromking.models.paytm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaytmResponseDataModel {
    @SerializedName("resultInfo")
    @Expose
    private PaytmResultBodyModel paytmResultData;
    @SerializedName("txnToken")
    @Expose
    private String txnToken;
    @SerializedName("isPromoCodeValid")
    @Expose
    private boolean isPromoCodeValid;
    @SerializedName("authenticated")
    @Expose
    private boolean authenticated;

    public PaytmResultBodyModel getPaytmResultData() {
        return paytmResultData;
    }

    public void setPaytmResultData(PaytmResultBodyModel paytmResultData) {
        this.paytmResultData = paytmResultData;
    }

    public String getTxnToken() {
        return txnToken;
    }

    public void setTxnToken(String txnToken) {
        this.txnToken = txnToken;
    }

    public boolean isPromoCodeValid() {
        return isPromoCodeValid;
    }

    public void setPromoCodeValid(boolean promoCodeValid) {
        isPromoCodeValid = promoCodeValid;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}
