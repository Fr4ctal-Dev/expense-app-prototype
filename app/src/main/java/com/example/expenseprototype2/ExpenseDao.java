package com.example.expenseprototype2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;


@Dao
public interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Expense expense);

    @Query("SELECT * from expense_table")
    LiveData<List<Expense>> getExpense();


}
