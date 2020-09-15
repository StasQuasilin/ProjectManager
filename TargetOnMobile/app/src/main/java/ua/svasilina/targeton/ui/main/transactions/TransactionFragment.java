package ua.svasilina.targeton.ui.main.transactions;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import ua.svasilina.targeton.R;
import ua.svasilina.targeton.adapters.TransactionsAdapter;
import ua.svasilina.targeton.dialogs.transactions.TransactionEditDialog;
import ua.svasilina.targeton.entity.transactions.Transaction;
import ua.svasilina.targeton.entity.transactions.UserData;
import ua.svasilina.targeton.ui.login.LoginActivity;
import ua.svasilina.targeton.ui.main.ApplicationFragment;
import ua.svasilina.targeton.utils.connection.Connector;
import ua.svasilina.targeton.utils.constants.API;
import ua.svasilina.targeton.utils.constants.Keys;
import ua.svasilina.targeton.utils.storage.UserAccessStorage;
import ua.svasilina.targeton.utils.storage.UserDataStorage;
import ua.svasilina.targeton.utils.subscribes.DataHandler;
import ua.svasilina.targeton.utils.subscribes.Subscribe;
import ua.svasilina.targeton.utils.subscribes.Subscriber;
import ua.svasilina.targeton.utils.subscribes.updaters.TransactionUpdater;

import static ua.svasilina.targeton.utils.constants.Keys.ID;
import static ua.svasilina.targeton.utils.constants.Keys.STATUS;
import static ua.svasilina.targeton.utils.constants.Keys.SUCCESS;
import static ua.svasilina.targeton.utils.constants.Keys.USER;

public class TransactionFragment extends ApplicationFragment {

    private Subscriber subscriber = Subscriber.getInstance();
    private Context context;

    private final DataHandler handler;

    public TransactionFragment(Context context) {
        this.context = context;
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

    private ListView transactionList;

    @Override
    public void onResume() {
        super.onResume();
        subscriber.subscribe(Subscribe.transactions, handler, context);
        System.out.println(adapter);
        System.out.println(transactionList);
    }

    @Override
    public void onPause() {
        super.onPause();
        Toast.makeText(getContext(), "TransactionFragment pause", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        subscriber.unsubscribe(Subscribe.transactions);
        Toast.makeText(getContext(), "TransactionFragment stop", Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.transactions_fragment, container, false);

        transactionList = view.findViewById(R.id.transactionList);
        adapter = new TransactionsAdapter(context, R.layout.transaction_list_item, inflater);
        transactionList.setAdapter(adapter);
        transactionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Transaction transaction = adapter.getItem(position);
                editTransaction(transaction);
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
        editTransaction(new Transaction());
    }

    private void editTransaction(final Transaction transaction){
        final Connector instance = Connector.getInstance();
        final Context ctx = getContext();
        final HashMap<String, Object> param = new HashMap<>();
        param.put(USER, UserAccessStorage.getUserAccess(ctx));
        instance.newJsonReq(ctx, API.USER_DATA, new JSONObject(param), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("??" + response);
                try {
                    final String status = response.getString(STATUS);
                    if (status.equals(SUCCESS)){
                        final UserData userData = new UserData(response);
                        editTransaction(transaction, userData);
                        UserDataStorage.saveUserData(ctx, response.toString());
                    } else {
                        final String reason = response.getString(Keys.REASON);
                        if (reason.equals(Keys.UNAUTHORISED)){
                            startActivity(ctx, LoginActivity.class);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
                try {
                    final UserData userData = new UserData(new JSONObject(UserDataStorage.getUserData(ctx)));
                    editTransaction(transaction, userData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void editTransaction(Transaction transaction, UserData userData) {

        final TransactionEditDialog ted = new TransactionEditDialog(getContext(), transaction, getLayoutInflater(), userData);
        ted.setCancelable(false);
        ted.show(getParentFragmentManager(), "Transaction Edit");
    }

    @Override
    public String getTitle() {
        return "Transactions";
    }

    public void sort() {
        adapter.sort();
    }
}
