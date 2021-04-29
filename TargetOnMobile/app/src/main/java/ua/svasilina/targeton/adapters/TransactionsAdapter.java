package ua.svasilina.targeton.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Comparator;
import java.util.LinkedList;

import ua.svasilina.targeton.R;
import ua.svasilina.targeton.entity.Account;
import ua.svasilina.targeton.entity.transactions.Transaction;
import ua.svasilina.targeton.entity.transactions.TransactionType;
import ua.svasilina.targeton.utils.builders.DateTimeBuilder;

import static android.view.View.GONE;
import static ua.svasilina.targeton.utils.constants.Constants.DATE_PATTERN;
import static ua.svasilina.targeton.utils.constants.Keys.EQUALS;
import static ua.svasilina.targeton.utils.constants.Keys.MINUS;
import static ua.svasilina.targeton.utils.constants.Keys.PLUS;
import static ua.svasilina.targeton.utils.constants.Keys.RIGHT_ARROW;
import static ua.svasilina.targeton.utils.constants.Keys.SPACE;
import static ua.svasilina.targeton.utils.constants.Keys.TIMES;

public class TransactionsAdapter extends ArrayAdapter<Transaction> {

    private final Context context;
    private final int resource;
    private final LayoutInflater inflater;
    private final DateTimeBuilder dtb = new DateTimeBuilder(DATE_PATTERN);

    public TransactionsAdapter(@NonNull Context context, int resource, LayoutInflater inflater) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.inflater = inflater;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final View view = convertView == null ? inflater.inflate(resource, parent, false) : convertView;
        final Transaction item = getItem(position);
        if (item != null) {
            final TextView dateView = view.findViewById(R.id.transactionDate);

            boolean showData = position == 0;
            if (!showData){
                final Transaction prev = getItem(position - 1);
                if (prev != null){
                    showData = !prev.getDate().equals(item.getDate());
                }
            }

            if (showData){
                dateView.setVisibility(View.VISIBLE);
                dateView.setText(dtb.build(item.getDate()));
            } else {
                dateView.setVisibility(GONE);
            }
            final TransactionType type = item.getType();
            final TextView transactionID = view.findViewById(R.id.transactionTitle);

            if(type ==  TransactionType.income || type == TransactionType.spending) {
                transactionID.setVisibility(View.VISIBLE);
                final String title = item.getTitle();
                if (title.isEmpty()) {
                    transactionID.setTextColor(Color.GRAY);
                    transactionID.setText(context.getResources().getString(R.string.without_category));
                } else {
                    transactionID.setTextColor(Color.BLACK);
                    transactionID.setText(title);
                }
            } else {
                transactionID.setVisibility(GONE);
            }


            final TextView accountFromView = view.findViewById(R.id.accountFrom);
            final Account accountFrom = item.getAccountFrom();
            if (accountFrom != null){
                accountFromView.setVisibility(View.VISIBLE);
                accountFromView.setText(accountFrom.getTitle() + SPACE + RIGHT_ARROW);
            } else {
                accountFromView.setVisibility(GONE);
            }

            final TextView accountToView = view.findViewById(R.id.accountTo);
            final Account accountTo = item.getAccountTo();
            if (accountTo != null){
                accountToView.setVisibility(View.VISIBLE);
                accountToView.setText(RIGHT_ARROW + SPACE + accountTo.getTitle());
            } else {
                accountToView.setVisibility(GONE);
            }

            final TextView transactionAmount = view.findViewById(R.id.amountView);
            transactionAmount.setText(buildSumString(item));
            final Resources resources = context.getResources();
            switch (item.getType()){
                case spending:
                    transactionAmount.setTextColor(resources.getColor(R.color.transaction_spending));
                    break;
                case income:
                    transactionAmount.setTextColor(resources.getColor(R.color.transaction_income));
            }
        }
        return view;
    }

    private final StringBuilder builder = new StringBuilder();
    private String buildSumString(Transaction transaction){
        builder.delete(0, builder.capacity());

        switch (transaction.getType()){
            case spending:
                builder.append(MINUS).append(SPACE);
                break;
            case income:
                builder.append(PLUS).append(SPACE);
        }

        builder.append(transaction.getAmount());
        if(transaction.getRate() != 1){
            builder.append(SPACE).append(TIMES);
            builder.append(transaction.getRate());
            builder.append(EQUALS);
            builder.append(transaction.getAmount() * transaction.getRate());
        }
        builder.append(SPACE).append(transaction.getCurrency());
        return builder.toString();
    }

    public void sort() {
        sort(new Comparator<Transaction>() {
            @Override
            public int compare(Transaction o1, Transaction o2) {
                return o2.compareTo(o1);
            }
        });
    }
}
