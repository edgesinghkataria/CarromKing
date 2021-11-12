package com.entwik.carromcash.models.debit;

import com.entwik.carromcash.models.common.ResponseErrorModel;
import com.entwik.carromcash.models.paytm.PaytmResponseDataModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DebitResponseModel {
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private DebitResponseDataModel data;
    @SerializedName("error")
    @Expose
    private ResponseErrorModel error;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public DebitResponseDataModel getData() {
        return data;
    }

    public void setData(DebitResponseDataModel data) {
        this.data = data;
    }

    public ResponseErrorModel getError() {
        return error;
    }

    public void setError(ResponseErrorModel error) {
        this.error = error;
    }
}
