package com.entwik.carromcash.models.debit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DebitInitRequestModel {
    @SerializedName("amount")
    @Expose
    String amount;

    public DebitInitRequestModel(String amount) {
            this.amount = amount;
        }

    public String getAmount() {
            return amount;
        }

    public void setAmount(String amount) {
            this.amount = amount;
        }
}
