package com.example.expenseprototype2;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.expenseprototype2.Services.ApiUtils;

import java.util.ArrayList;
import java.util.List;

public class ExpenseViewModel extends AndroidViewModel {

    private final ExpenseRepo expenseRepo;

    private final LiveData<List<Expense>> allExpenses;

    public ExpenseViewModel(Application application) {
        super(application);
        expenseRepo = new ExpenseRepo(application);
        allExpenses = expenseRepo.getAllExpenses();
    }

    private LiveData<List<Expense>> syncOnlineExpenses() {
        ApiUtils utils = new ApiUtils();
        return utils.setExpenses("", ""); //TODO Login
    }

    public LiveData<List<Expense>> getAllExpenses() throws InterruptedException {

        final LiveData<List<Expense>> onlineExpenses = syncOnlineExpenses();
        onlineExpenses.observeForever(new Observer<List<Expense>>() {

            public ArrayList<Expense> getOfflineExpenses() {
                ArrayList<Expense> offlineExpenses = new ArrayList<>();
                for (Expense i : allExpenses.getValue()) {
                    int iterator = 0;
                    for (Expense j : onlineExpenses.getValue()) {
                        if (!i.getSyncId().equals(j.getSyncId())) {
                            if (iterator + 1 == onlineExpenses.getValue().size()) {
                                //last element
                                offlineExpenses.add(j);
                            }
                            iterator++;
                        }
                    }
                }
                return offlineExpenses;
            }

            @Override
            public void onChanged(List<Expense> expenses) {
                ArrayList<String> syncedIds = new ArrayList<>();
                for (Expense i :
                        allExpenses.getValue()) {
                    syncedIds.add(i.getSyncId());
                }
                for (Expense i :
                        expenses) {
                    if (syncedIds.contains(i.getSyncId())) {
                        break;
                    }
                    insert(i);
                    allExpenses.getValue().add(i);

                }
                for (Expense i :
                        getOfflineExpenses()) {
                    allExpenses.getValue().add(i);
                }
                onlineExpenses.removeObserver(this);
            }
        });
        return allExpenses;
    }

    public void insert(final Expense expense) {

        if (expense.getState() != ExpenseSyncState.SYNCED) {
            ApiUtils utils = new ApiUtils();
            LiveData<Integer> createCode = utils.postExpense("", "", expense);//TODO Login
            createCode.observeForever(new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    if (integer == 201)
                        expense.setState(ExpenseSyncState.SYNCED);
                    expenseRepo.insert(expense);

                }
            });

        } else {
            expenseRepo.insert(expense);
        }
    }


}
