package ua.svasilina.targeton.dialogs.accounts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;

import ua.svasilina.targeton.R;
import ua.svasilina.targeton.entity.Account;

public class AccountEditDialog extends DialogFragment {

    private final Context context;
    private final LayoutInflater inflater;
    private final Account account;
    private EditText accountName;
    private Spinner accountType;
    private View sumGroup;
    private EditText accountSum;
    private Spinner accountCurrency;


    public AccountEditDialog(Context context, LayoutInflater inflater, Account account) {
        this.context = context;
        this.inflater = inflater;
        this.account = account;
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View view = inflater.inflate(R.layout.account_edit, null);




        builder.setView(view);
        builder.setTitle(R.string.account_edit);
        builder.setNegativeButton(R.string.cancel, null);
        builder.setPositiveButton(R.string.save, null);
        return builder.create();
    }
}
