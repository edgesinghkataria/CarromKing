package com.entwik.carromcash.models.debit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DebitResponseDataModel {
    @SerializedName("orderId")
    @Expose
    String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}


