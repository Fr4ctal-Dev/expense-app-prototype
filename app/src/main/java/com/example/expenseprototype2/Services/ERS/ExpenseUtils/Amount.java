package com.example.expenseprototype2.Services.ERS.ExpenseUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Amount {
    @SerializedName("value")
    @Expose
    private double amount;

    @SerializedName("currencyCode")
    @Expose
    private String currency;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
