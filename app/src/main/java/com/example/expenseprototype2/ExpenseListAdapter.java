package com.example.expenseprototype2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.Currency;
import java.util.List;

public class ExpenseListAdapter extends RecyclerView.Adapter<ExpenseListAdapter.ExpenseViewHolder> {

    private final LayoutInflater inflater;
    private List<Expense> expenses;

    ExpenseListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @SuppressLint("SetTextI18n")
    @Override
    //add UI elements to UI view holder
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        DecimalFormat df2 = new DecimalFormat("0.00#######");
        Expense currentExpense = expenses.get(position);
        String countryCode = currentExpense.getCurrency().split(" ")[0];

        if (countryCode.equals("EUR")) {
            holder.amountTv.setText(df2.format(currentExpense.getAmount()) + " €");
        } else {
            Currency cur = Currency.getInstance(countryCode);
            holder.amountTv.setText(cur.getSymbol() + " " + df2.format(currentExpense.getAmount()));
        }
        holder.reasonTv.setText(currentExpense.getReason());

        holder.typeTv.setText(currentExpense.getType());

        if (currentExpense.getState() == ExpenseSyncState.SYNCED)
            holder.syncStatusImageView.setImageResource(R.drawable.ic_sync_black_24dp);
        else
            holder.syncStatusImageView.setImageResource(R.drawable.ic_sync_problem_black_24dp);

    }

    @NonNull
    @Override
    // inflate expense view and return finished Holder
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View expenseView = inflater.inflate(R.layout.expense_layout, parent, false);
        return new ExpenseViewHolder(expenseView);
    }

    @Override
    //get amount of expenses
    public int getItemCount() {
        if (expenses != null) {
            return expenses.size();
        } else return 0;
    }

    // set expenses to provided List from back
    void setItems(List<Expense> expenseList) {
        expenses = expenseList;
        notifyDataSetChanged();
    }

    //Holder subclass
    class ExpenseViewHolder extends RecyclerView.ViewHolder {
        private final TextView amountTv;
        private final TextView reasonTv;
        private final TextView typeTv;
        private final ImageView syncStatusImageView;

        private ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            amountTv = itemView.findViewById(R.id.amount_textView);
            reasonTv = itemView.findViewById(R.id.reason_textView);
            typeTv = itemView.findViewById(R.id.type_textview);
            syncStatusImageView = itemView.findViewById(R.id.sync_status_imageview);


        }
    }

}
