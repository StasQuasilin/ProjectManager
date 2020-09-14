package ua.svasilina.targeton.ui.main.transactions;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import org.json.JSONArray;
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
import ua.svasilina.targeton.utils.subscribes.Subscribe;
import ua.svasilina.targeton.utils.subscribes.Subscriber;

import static ua.svasilina.targeton.utils.constants.Constants.ADD;
import static ua.svasilina.targeton.utils.constants.Constants.UPDATE;
import static ua.svasilina.targeton.utils.constants.Constants.USER;
import static ua.svasilina.targeton.utils.constants.Keys.ERROR;
import static ua.svasilina.targeton.utils.constants.Keys.STATUS;
import static ua.svasilina.targeton.utils.constants.Keys.SUCCESS;

public class TransactionFragment extends ApplicationFragment {

    private Subscriber subscriber = Subscriber.getInstance();
    private Context context;

    public TransactionFragment() {
        context = getActivity();
    }

    public TransactionFragment(Context context, LayoutInflater inflater) {
        this.context = context;
    }

    private TransactionsAdapter adapter;

    @Override
    public void onStart() {
        super.onStart();
        if (context == null){
            System.out.println("Context is null");
        }

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
                            adapter.sort();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (data.containsKey(UPDATE)){
                    final String string = data.getString(UPDATE);
                    System.out.println("?!" + string);
                }

                return false;
            }
        });

        subscriber.subscribe(Subscribe.transactions, handler, context);
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
        subscriber.unsubscribe(Subscribe.transactions);
        Toast.makeText(getContext(), "TransactionFragment stop", Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.transactions_fragment, container, false);

        ListView transactionList = view.findViewById(R.id.transactionList);
        final FragmentActivity activity = getActivity();
        if (activity != null) {
            adapter = new TransactionsAdapter(activity, R.layout.transaction_list_item, inflater);
        }
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
}
