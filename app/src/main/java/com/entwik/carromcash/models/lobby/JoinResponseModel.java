package com.entwik.carromcash.models.lobby;

import com.entwik.carromcash.models.common.ResponseErrorModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JoinResponseModel {
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private JoinResponseDataModel data;
    @SerializedName("error")
    @Expose
    private ResponseErrorModel error;

    public ResponseErrorModel getError() {
        return error;
    }

    public void setError(ResponseErrorModel error) {
        this.error = error;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public JoinResponseDataModel getData() {
        return data;
    }

    public void setData(JoinResponseDataModel data) {
        this.data = data;
    }
}
