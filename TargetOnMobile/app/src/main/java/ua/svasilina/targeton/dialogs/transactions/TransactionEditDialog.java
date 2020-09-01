package ua.svasilina.targeton.dialogs.transactions;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.json.JSONArray;

import java.util.Calendar;
import java.util.Date;

import ua.svasilina.targeton.R;
import ua.svasilina.targeton.dialogs.SearchDialog;
import ua.svasilina.targeton.dialogs.SearchResult;
import ua.svasilina.targeton.dialogs.common.DateDialog;
import ua.svasilina.targeton.entity.Category;
import ua.svasilina.targeton.entity.Transaction;
import ua.svasilina.targeton.utils.builders.DateTimeBuilder;
import ua.svasilina.targeton.utils.constants.API;
import ua.svasilina.targeton.utils.listeners.ChangeListener;

import static ua.svasilina.targeton.utils.constants.Constants.DATE_PATTERN;

public class TransactionEditDialog extends DialogFragment {


    private final LayoutInflater inflater;
    private final Transaction transaction;
    private Button dateButton;
    private Button transactionCategory;


    public TransactionEditDialog(Transaction transaction, LayoutInflater inflater) {
        this.transaction = transaction;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View view = inflater.inflate(R.layout.transaction_edit, null);

        dateButton = view.findViewById(R.id.dateButton);
        transactionCategory = view.findViewById(R.id.transactionCategory);
        initDateButton();
        initTransactionCategory();

        builder.setView(view);
        builder.setTitle(R.string.transaction_edit);
        builder.setPositiveButton(R.string.save, null);
        builder.setNegativeButton(R.string.cancel, null);
        return builder.create();
    }


    DateTimeBuilder dateTimeBuilder = new DateTimeBuilder(DATE_PATTERN);

    private void initDateButton() {
        final Calendar d = transaction.getDate();
        final Calendar date = d == null ? Calendar.getInstance() : d;

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DateDialog(date, getLayoutInflater(), DateDialog.DialogType.calendar, new ChangeListener() {
                    @Override
                    public void onChange() {
                        updateDateButton();
                    }
                }).show(getParentFragmentManager(), "DateDialog");
            }
        });
        if (d == null){
            transaction.setDate(date);
        }
        updateDateButton();
    }

    private void updateDateButton() {
        dateButton.setText(dateTimeBuilder.build(transaction.getDate()));
    }

    private void initTransactionCategory() {
        Category category = transaction.getCategory();
        if (category == null){
            category = new Category();
            transaction.setCategory(category);
        }

        transactionCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SearchDialog<Category> dialog = new SearchDialog<>(getContext(), getLayoutInflater(), API.FIND_CATEGORY, new OnChangeListener<Category>() {
                    @Override
                    public void onChange(Category item) {
                        transaction.setCategory(item);
                        updateTransactionCategory();
                    }
                });
                dialog.show(getParentFragmentManager(), "Find Category");

            }
        });
        updateTransactionCategory();
    }

    private void updateTransactionCategory() {
        transactionCategory.setText(transaction.getCategory().getTitle());
    }
}
