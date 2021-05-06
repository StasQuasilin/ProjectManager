package ua.svasilina.targeton.ui.main.transactions;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import ua.svasilina.targeton.MainActivity;
import ua.svasilina.targeton.R;
import ua.svasilina.targeton.adapters.TransactionsAdapter;
import ua.svasilina.targeton.entity.transactions.Transaction;
import ua.svasilina.targeton.ui.main.ApplicationPage;
import ua.svasilina.targeton.ui.main.Pages;
import ua.svasilina.targeton.utils.subscribes.DataHandler;
import ua.svasilina.targeton.utils.subscribes.Subscribe;
import ua.svasilina.targeton.utils.subscribes.Subscriber;
import ua.svasilina.targeton.utils.subscribes.updaters.TransactionUpdater;

import static ua.svasilina.targeton.utils.constants.Keys.ID;

public class TransactionFragment extends ApplicationPage {

    private final MainActivity mainActivity;
    private final Subscriber subscriber = Subscriber.getInstance();
    private final Context context;

    private final DataHandler handler;

    public TransactionFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.context = mainActivity.getApplicationContext();
        handler = new DataHandler(new TransactionUpdater(this));
    }

    private TransactionsAdapter adapter;
    private final HashMap<Integer, Transaction> transactionHashMap = new HashMap<>();

    @Override
    public void onStart() {
        super.onStart();
    }

    public void update(JSONObject json) throws JSONException {
        final int id = json.getInt(ID);
        Transaction transaction = transactionHashMap.get(id);
        if (transaction == null){
            transaction = new Transaction(json);
            transactionHashMap.put(transaction.getId(), transaction);
            adapter.add(transaction);
        } else {
            transaction.update(json);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        subscriber.subscribe(Subscribe.transactions, handler, context);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        subscriber.unsubscribe(Subscribe.transactions);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.transactions_fragment, container, false);

        ListView transactionList = view.findViewById(R.id.transactionList);
        adapter = new TransactionsAdapter(context, R.layout.transaction_list_item, inflater);
        transactionList.setAdapter(adapter);
        transactionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Transaction transaction = adapter.getItem(position);
                if (transaction != null) {
                    editTransaction(transaction.getId());
                } else {
                    newTransaction();
                }
            }
        });
        final FloatingActionButton newTransaction = view.findViewById(R.id.newTransaction);
        newTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newTransaction();
            }
        });
        return view;
    }

    private void newTransaction() {
        editTransaction(-1);
    }

    private void editTransaction(int transaction) {
        mainActivity.openPage(Pages.transactionEdit, transaction);
    }

    @Override
    public String getTitle() {
        return context.getResources().getString(R.string.transactions_title);
    }

    public void sort() {
        adapter.sort();
    }
}
