package ua.svasilina.targeton.ui.main.accounts;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ua.svasilina.targeton.R;
import ua.svasilina.targeton.adapters.SimpleListAdapter;
import ua.svasilina.targeton.dialogs.SearchListBuilder;
import ua.svasilina.targeton.dialogs.transactions.SearchDialogItemBuilder;
import ua.svasilina.targeton.entity.Account;
import ua.svasilina.targeton.ui.main.ApplicationFragment;
import ua.svasilina.targeton.utils.constants.Keys;
import ua.svasilina.targeton.utils.subscribes.DataHandler;
import ua.svasilina.targeton.utils.subscribes.Subscribe;
import ua.svasilina.targeton.utils.subscribes.Subscriber;
import ua.svasilina.targeton.utils.subscribes.updaters.AccountsUpdater;

public class AccountsFragment extends ApplicationFragment {

    private Subscriber subscriber = Subscriber.getInstance();
    private DataHandler handler;
    private TextView accountsTotal;
    private ListView accountsList;
    private SimpleListAdapter<Account> adapter;

    private final Context context;

    public AccountsFragment(Context context) {
        this.context = context;
        handler = new DataHandler(new AccountsUpdater(this));

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.accounts, container, false);
        accountsTotal = view.findViewById(R.id.accountsTotal);
        accountsList = view.findViewById(R.id.accountList);
        adapter = new SimpleListAdapter<>(context, R.layout.account_view, inflater, new SearchListBuilder<Account>() {
            @Override
            public void build(Account item, View view) {
                final TextView accountNameView = view.findViewById(R.id.accountName);
                final TextView accountValue = view.findViewById(R.id.accountValue);

                accountNameView.setText(item.getTitle());
                StringBuilder builder = new StringBuilder();
                final double amount = item.getAmount();
                final int limit = item.getLimit();

                builder.append(item.getAmount() + limit);

                if (limit > 0){
                    builder.append(Keys.SPACE).append(Keys.LEFT_BRACE);
                    builder.append(amount).append(Keys.SPACE).append(Keys.PLUS);
                    builder.append(limit).append(Keys.RIGHT_BRACE);
                }

                builder.append(Keys.SPACE).append(item.getCurrency());

                accountValue.setText(builder.toString());
            }
        }, new SearchDialogItemBuilder<Account>() {
            @Override
            public Account create(JSONObject json) {
                return new Account(json);
            }
        });
        accountsList.setAdapter(adapter);
        return view;
    }

    private final HashMap<Integer, Account> accountHashMap = new HashMap<>();

    public void update(JSONObject object){
        try {
            final int id = object.getInt(Keys.ID);
            Account account = accountHashMap.get(id);
            if (account == null){
                account = new Account(object);
                accountHashMap.put(account.getId(), account);
                adapter.add(account);
            } else {
                account.update(object);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        computeTotal();
    }

    private void computeTotal() {
        final HashMap<String, Float> total = new HashMap<>();
        for (Account account : accountHashMap.values()){
            final String currency = account.getCurrency();
            Float aFloat = total.get(currency);
            if (aFloat == null){
                aFloat = 0f;
            }
            total.put(currency, (float) (aFloat + account.getAmount()));
        }

        StringBuilder builder = new StringBuilder();
        if (total.size() > 0) {
            int i = 0;
            for (Map.Entry<String, Float> entry : total.entrySet()) {
                builder.append(entry.getValue()).append(Keys.SPACE).append(entry.getKey());
                if (i < total.size() - 1) {
                    builder.append(Keys.COMMA).append(Keys.SPACE);
                }
                i++;
            }
        } else {
            builder.append(0);
        }
        accountsTotal.setText(builder.toString());
    }

    @Override
    public void onResume() {
        super.onResume();
        subscriber.subscribe(Subscribe.accounts, handler, context);
    }

    @Override
    public void onPause() {
        super.onPause();
        subscriber.unsubscribe(Subscribe.accounts);
    }

    @Override
    public String getTitle() {
        return context.getResources().getString(R.string.accounts_title);
    }
}
