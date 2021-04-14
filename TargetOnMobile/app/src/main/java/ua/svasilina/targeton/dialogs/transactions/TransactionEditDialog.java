package ua.svasilina.targeton.dialogs.transactions;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import ua.svasilina.targeton.R;
import ua.svasilina.targeton.entity.transactions.Transaction;
import ua.svasilina.targeton.entity.transactions.TransactionType;
import ua.svasilina.targeton.entity.transactions.UserData;
import ua.svasilina.targeton.utils.connection.Connector;
import ua.svasilina.targeton.utils.constants.API;

public class TransactionEditDialog extends DialogFragment {

    private Context context;
    private LayoutInflater inflater;
    private Transaction transaction;
    private UserData userData;


    private TransactionEditInitializer initializer;

    public TransactionEditDialog(Context context, Transaction transaction, LayoutInflater inflater, UserData userData) {
        initializer = new TransactionEditInitializer(context, transaction, userData, getParentFragmentManager());
        this.context = context;
        this.transaction = transaction;
        this.inflater = inflater;
        this.userData = userData;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final View view = inflater.inflate(R.layout.transaction_edit, null);

        initializer.initView(view);

        builder.setView(view);
        builder.setTitle(R.string.transaction_edit);
        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                save();
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        return builder.create();
    }

    private void save() {
//        final Editable amountText = amount.getText();
//        float amount = Float.parseFloat(amountText.toString());
//        transaction.setAmount(amount);
//        transaction.setRate(1);

        final TransactionType type = transaction.getType();
        switch (type){
            case spending:
                transaction.setAccountTo(null);
                break;
            case income:
                transaction.setAccountFrom(null);
                break;
        }

        final Connector instance = Connector.getInstance();
        instance.newJsonReq(getContext(), API.SAVE_TRANSACTION, transaction.buildJson(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
            }
        });
    }



























}
