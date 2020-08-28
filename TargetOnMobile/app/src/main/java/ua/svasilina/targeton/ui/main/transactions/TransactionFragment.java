package ua.svasilina.targeton.ui.main.transactions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ua.svasilina.targeton.R;
import ua.svasilina.targeton.adapters.TransactionsAdapter;
import ua.svasilina.targeton.dialogs.transactions.TransactionEditDialog;
import ua.svasilina.targeton.entity.Transaction;
import ua.svasilina.targeton.ui.main.ApplicationFragment;
import ua.svasilina.targeton.utils.subscribes.Subscribe;
import ua.svasilina.targeton.utils.subscribes.Subscriber;

import static ua.svasilina.targeton.utils.constants.Constants.ADD;

public class TransactionFragment extends ApplicationFragment {

    private Subscriber subscriber = Subscriber.getInstance();
    private final Context context;

    public TransactionFragment(Context context, LayoutInflater inflater) {
        this.context = context;
    }

    private TransactionsAdapter adapter;

    @Override
    public void onStart() {
        super.onStart();

        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                final Bundle data = msg.getData();
                if (data.containsKey(ADD)){
                    final String add = data.getString(ADD);
                    try {
                        if (add != null) {
                            final JSONArray jsonArray = new JSONArray(add);
                            for (int i = 0; i < jsonArray.length(); i++){
                                final JSONObject json = jsonArray.getJSONObject(i);
                                adapter.add(new Transaction(json));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                return false;
            }
        });

        subscriber.subscribe(Subscribe.transactions, handler);
    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getContext(), "TransactionFragment resume", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        Toast.makeText(getContext(), "TransactionFragment pause", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        subscriber.unSubscribe(Subscribe.transactions);
        Toast.makeText(getContext(), "TransactionFragment stop", Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.transactions_fragment, container, false);

        ListView transactionList = view.findViewById(R.id.transactionList);
        adapter = new TransactionsAdapter(context, R.layout.transaction_list_item, inflater);
        transactionList.setAdapter(adapter);
        final FloatingActionButton newTransaction = view.findViewById(R.id.newTransaction);
        newTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newTransaction();
            }
        });
//        adapter.notifyDataSetChanged();
        return view;
    }

    private void newTransaction() {
        final TransactionEditDialog transactionEditDialog = new TransactionEditDialog(new Transaction(), getLayoutInflater());
        transactionEditDialog.show(getParentFragmentManager(), "Transaction Edit");
    }

    @Override
    public String getTitle() {
        return "Transactions";
    }
}
