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

import java.util.Calendar;
import java.util.Date;

import ua.svasilina.targeton.R;
import ua.svasilina.targeton.entity.Category;
import ua.svasilina.targeton.entity.Transaction;
import ua.svasilina.targeton.utils.builders.DateTimeBuilder;

import static ua.svasilina.targeton.utils.constants.Constants.DATE_PATTERN;

public class TransactionEditDialog extends DialogFragment {


    private final LayoutInflater inflater;
    private final Transaction transaction;
    private Button dateButton;
    private SearchView transactionCategory;

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

    private void initDateButton() {
        Date date = transaction.getDate();
        if (date == null){
            date = Calendar.getInstance().getTime();
        }

        DateTimeBuilder dateTimeBuilder = new DateTimeBuilder(DATE_PATTERN);
        dateButton.setText(dateTimeBuilder.build(date));


    }

    private void initTransactionCategory() {
        Category category = transaction.getCategory();
        if (category == null){
            category = new Category();
            transaction.setCategory(category);
        }
        transactionCategory.setIconified(false);
        
        transactionCategory.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return false;
            }
        });
    }
}
