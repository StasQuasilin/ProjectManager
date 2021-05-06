package ua.svasilina.targeton.dialogs.transactions;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import ua.svasilina.targeton.R;
import ua.svasilina.targeton.adapters.TypeListAdapter;
import ua.svasilina.targeton.entity.transactions.Transaction;
import ua.svasilina.targeton.entity.transactions.TransactionType;
import ua.svasilina.targeton.utils.NameUtil;
import ua.svasilina.targeton.utils.listeners.ChangeListener;

public class TransactionTypeDialog extends DialogFragment {

    private final LayoutInflater inflater;
    private final Context context;
    private RadioGroup typeGroup;
    private final Transaction transaction;
    private final ChangeListener listener;

    public TransactionTypeDialog(Context context, LayoutInflater inflater, Transaction transaction, ChangeListener listener) {
        this.inflater = inflater;
        this.context = context;
        this.transaction = transaction;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final View view = inflater.inflate(R.layout.transaction_type_dialog, null);

        typeGroup = view.findViewById(R.id.typeGroup);
        int id = 0;
        for (TransactionType t : TransactionType.values()){
            final RadioButton itemRadio = new RadioButton(context);
            itemRadio.setId(id++);
            typeGroup.addView(itemRadio);
            itemRadio.setText(NameUtil.getNameByString(context, t.toString()));
            itemRadio.setChecked(t== transaction.getType());
        }

        builder.setView(view);
        builder.setTitle(R.string.transaction_type);
        builder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                save();
            }
        });
        builder.setNegativeButton(R.string.cancel, null);

        return builder.create();
    }

    private void save() {
        final TransactionType type = TransactionType.values()[typeGroup.getCheckedRadioButtonId()];
        transaction.setType(type);
        listener.onChange();
    }
}
