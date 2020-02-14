package com.example.expenseprototype2;

import androidx.room.TypeConverter;


public class Converters {

    @TypeConverter
    public int fromExpenseSyncState(ExpenseSyncState expenseSyncState) {
        return expenseSyncState.ordinal();
    }

    @TypeConverter
    public ExpenseSyncState fromInt(int index) {
        return ExpenseSyncState.values()[index];
    }

}
