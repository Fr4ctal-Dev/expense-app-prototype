package com.example.expenseprototype2;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Expense.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ExpenseRoomDatabase extends RoomDatabase {
    private static ExpenseRoomDatabase INSTANCE;

    static ExpenseRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ExpenseRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ExpenseRoomDatabase.class, "expense_ArrayList_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract ExpenseDao itemDao();
}
