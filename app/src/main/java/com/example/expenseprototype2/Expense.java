package com.example.expenseprototype2;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.expenseprototype2.Services.ERS.ExpenseUtils.Amount;
import com.example.expenseprototype2.Services.ERS.ExpenseUtils.Type;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;


@Entity(tableName = "expense_table")
public class Expense {


    @SerializedName("businessPurpose")
    @Expose
    private final String reason;
    @TypeConverters(Converters.class)
    @PrimaryKey
    @NonNull
    private String id;
    @SerializedName("expenseId")
    @Expose
    private String syncId;
    @SerializedName("transactionAmount")
    @Expose
    @Ignore
    private Amount amountObj;
    @SerializedName("expenseType")
    @Expose
    @Ignore
    private Type typeObj;

    private String type;
    private double amount;
    private String currency;
    private ExpenseSyncState state;


    public Expense(double amount, String currency, String reason, String type) {
        this.amount = amount;
        this.currency = currency;
        this.reason = reason;
        this.type = type.toUpperCase();
        this.id = UUID.randomUUID().toString();
        this.syncId = UUID.randomUUID().toString();
        this.state = ExpenseSyncState.UNSYNCED;

    }

    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getSyncId() {
        return syncId;
    }

    public void setSyncId(String syncId) {
        this.syncId = syncId;
    }

    public Amount getAmountObj() {
        return amountObj;
    }

    public void setAmountObj(Amount amountObj) {
        this.amountObj = amountObj;
    }

    public String getReason() {
        return reason;
    }

    public Type getTypeObj() {
        return typeObj;
    }

    public void setTypeObj(Type typeObj) {
        this.typeObj = typeObj;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public ExpenseSyncState getState() {
        return state;
    }

    public void setState(ExpenseSyncState state) {
        this.state = state;
    }
}
