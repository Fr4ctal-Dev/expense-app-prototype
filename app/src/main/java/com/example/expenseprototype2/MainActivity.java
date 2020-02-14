package com.example.expenseprototype2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //RecyclerView setup
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final ExpenseListAdapter adapter = new ExpenseListAdapter(this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager l = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(l);

        //Toolbar Button setup
        ImageView addIb = findViewById(R.id.add_expense_button_main);
        addIb.setImageDrawable(getDrawable(R.drawable.ic_add_white_24dp));
        addIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewExpenseActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        ExpenseViewModel expenseViewModel = ViewModelProviders.of(this).get(ExpenseViewModel.class);
        try {
            expenseViewModel.getAllExpenses().observe(this, new Observer<List<Expense>>() {
                @Override
                public void onChanged(List<Expense> expenses) {
                    adapter.setItems(expenses);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
