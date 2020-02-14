package com.example.expenseprototype2;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class NewExpenseActivity extends AppCompatActivity {
    private EditText amountEt;
    private Spinner currencySpinner;
    private EditText reasonEt;
    private Spinner typeSpinner;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_expense);

        amountEt = findViewById(R.id.amountTf);
        currencySpinner = findViewById(R.id.currencySpinner);
        reasonEt = findViewById(R.id.reasonTf);
        typeSpinner = findViewById(R.id.type_spinner);


        ImageView saveBtn = findViewById(R.id.save_button);
        saveBtn.setImageDrawable(getDrawable(R.drawable.ic_check_white_24dp));
        saveBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ExpenseViewModel expenseViewModel = ViewModelProviders.of(NewExpenseActivity.this).get(ExpenseViewModel.class);
                // Empty amount, set "CANCEL" reply
                if (TextUtils.isEmpty(amountEt.getText())) {
                    setResult(RESULT_CANCELED);
                }
                // Add data to db directly over ViewModel
                else {
                    double amount = Double.parseDouble(amountEt.getText().toString());
                    String currencySpinnerStr = currencySpinner.getSelectedItem().toString();
                    String reason = reasonEt.getText().toString();
                    String typeSpinnerStr = typeSpinner.getSelectedItem().toString();


                    Expense newExpense = new Expense(amount, currencySpinnerStr, reason, typeSpinnerStr);

                    expenseViewModel.insert(newExpense);

                    setResult(RESULT_OK);
                }
                finish();
            }
        });
    }
}
