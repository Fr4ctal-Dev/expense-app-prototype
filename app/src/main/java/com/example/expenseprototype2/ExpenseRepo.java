package com.example.expenseprototype2;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

class ExpenseRepo {
    private final ExpenseDao expenseDao;
    private final LiveData<List<Expense>> expensesList;

    ExpenseRepo(Application app) {
        ExpenseRoomDatabase db = ExpenseRoomDatabase.getDatabase(app);

        expenseDao = db.itemDao();
        expensesList = expenseDao.getExpense();
    }

    LiveData<List<Expense>> getAllExpenses() {
        return expensesList;
    }

    void insert(Expense expense) {
        new newAsyncTask(expenseDao).execute(expense);
    }

    //TODO RxJava / Coroutines instead of AsyncTask
    private static class newAsyncTask extends AsyncTask<Expense, Void, Void> {

        private final ExpenseDao asyncDao;

        newAsyncTask(ExpenseDao dao) {
            asyncDao = dao;
        }

        @Override
        protected Void doInBackground(final Expense... params) {
            asyncDao.insert(params[0]);
            return null;
        }

    }
}
