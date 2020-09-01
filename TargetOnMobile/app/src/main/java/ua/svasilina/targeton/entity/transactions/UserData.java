package ua.svasilina.targeton.entity.transactions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

import ua.svasilina.targeton.entity.Account;

import static ua.svasilina.targeton.utils.constants.Keys.ACCOUNTS;
import static ua.svasilina.targeton.utils.constants.Keys.CURRENCY;

public class UserData {

    private final LinkedList<Account> accounts = new LinkedList<>();
    private final LinkedList<String> currencies = new LinkedList<>();

    public UserData(JSONObject response) throws JSONException {
        final JSONArray accountsArr = response.getJSONArray(ACCOUNTS);
        for (int i = 0; i < accountsArr.length(); i++){
            accounts.add(new Account(accountsArr.getJSONObject(i)));
        }

        final JSONArray currencyArr = response.getJSONArray(CURRENCY);
        for (int i = 0; i < currencyArr.length(); i++){
            currencies.add(currencyArr.getString(i));
        }
    }

    public LinkedList<Account> getAccounts() {
        return accounts;
    }

    public LinkedList<String> getCurrencies() {
        return currencies;
    }
}
