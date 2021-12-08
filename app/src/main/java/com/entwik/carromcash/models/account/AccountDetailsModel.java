package com.entwik.carromcash.models.account;

public class AccountDetailsModel {
    private String upi;
    private String accountNumber;
    private String bankName;
    private String ifsc;

    public AccountDetailsModel(String upi, String accountNumber, String bankName, String ifsc) {
        this.upi = upi;
        this.accountNumber = accountNumber;
        this.bankName = bankName;
        this.ifsc = ifsc;
    }

    public String getUpi() {
        return upi;
    }

    public void setUpi(String upi) {
        this.upi = upi;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }
}
