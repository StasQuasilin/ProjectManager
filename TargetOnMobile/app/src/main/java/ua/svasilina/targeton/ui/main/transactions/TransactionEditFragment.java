package ua.svasilina.targeton.ui.main.transactions;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;

import ua.svasilina.targeton.MainActivity;
import ua.svasilina.targeton.R;
import ua.svasilina.targeton.dialogs.transactions.TransactionEditInitializer;
import ua.svasilina.targeton.entity.Account;
import ua.svasilina.targeton.entity.transactions.Transaction;
import ua.svasilina.targeton.entity.transactions.TransactionDetail;
import ua.svasilina.targeton.entity.transactions.UserData;
import ua.svasilina.targeton.ui.main.ApplicationPage;
import ua.svasilina.targeton.ui.main.Pages;
import ua.svasilina.targeton.utils.connection.Connector;
import ua.svasilina.targeton.utils.constants.API;
import ua.svasilina.targeton.utils.constants.Keys;
import ua.svasilina.targeton.utils.storage.StorageUtil;

import static ua.svasilina.targeton.utils.constants.Keys.DETAILS;
import static ua.svasilina.targeton.utils.constants.Keys.ID;

public class TransactionEditFragment extends ApplicationPage {

    private final MainActivity mainActivity;
    private final Context context;
    private TransactionEditInitializer transactionEditInitializer;
    private final Connector connector = new Connector();
    final HashMap<String, Object> args = new HashMap<>();
    View view;

    public TransactionEditFragment(MainActivity mainActivity, int attr, final FragmentManager fragmentManager) {
        this.mainActivity = mainActivity;
        context = mainActivity.getApplicationContext();


        args.put(ID, attr);
        connector.newJsonReq(context, API.SAVE_TRANSACTION + API.DATA, new JSONObject(args), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Transaction transaction = null;
                try {
                    transaction = new Transaction(response.getJSONObject(Keys.TRANSACTION));
                    final JSONArray detailsArray = response.getJSONArray(DETAILS);
                    final LinkedList<TransactionDetail> details = transaction.getDetails();
                    for (int i = 0; i < detailsArray.length(); i++){
                        details.add(new TransactionDetail(detailsArray.getJSONObject(i)));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                UserData userData = new UserData();
                try {
                    final JSONArray responseJSONArray = response.getJSONArray(Keys.ACCOUNTS);
                    for (int i = 0; i < responseJSONArray.length(); i++){
                        Account account = new Account(responseJSONArray.getJSONObject(i));
                        userData.addAccount(account);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    final JSONArray jsonArray = response.getJSONArray(Keys.CURRENCY);
                    for (int i = 0; i < jsonArray.length(); i++){
                        userData.addCurrency(jsonArray.getString(i));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                transactionEditInitializer = new TransactionEditInitializer(context, transaction, userData, fragmentManager);
                transactionEditInitializer.initView(view);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.transaction_edit, null);

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.transaction_menu_edit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final int itemId = item.getItemId();
        System.out.println(itemId);
        if (itemId == R.id.save_transaction){
            save();
        } else if(itemId == R.id.close_transaction){
            mainActivity.openPage(Pages.transactions, -1);
        }
        return super.onOptionsItemSelected(item);
    }

    private void save() {

    }

    @Override
    public String getTitle() {
        return context.getResources().getString(R.string.transactions_edit_title);
    }
}
