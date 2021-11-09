package com.entwik.carromcash.models.wallet;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionResponseDataModel implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("transactionId")
    @Expose
    private String transactionId;
    @SerializedName("amount")
    @Expose
    private int amount;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    protected TransactionResponseDataModel(Parcel in) {
        id = in.readInt();
        type = in.readString();
        name = in.readString();
        transactionId = in.readString();
        amount = in.readInt();
        status = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(type);
        dest.writeString(name);
        dest.writeString(transactionId);
        dest.writeInt(amount);
        dest.writeString(status);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TransactionResponseDataModel> CREATOR = new Creator<TransactionResponseDataModel>() {
        @Override
        public TransactionResponseDataModel createFromParcel(Parcel in) {
            return new TransactionResponseDataModel(in);
        }

        @Override
        public TransactionResponseDataModel[] newArray(int size) {
            return new TransactionResponseDataModel[size];
        }
    };

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public int getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @NonNull
    @Override
    public String toString() {
        return "TransactionResponseDataModel{" +
                "id="+ id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", amount=" + amount +
                ", state='" + status + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }

}
