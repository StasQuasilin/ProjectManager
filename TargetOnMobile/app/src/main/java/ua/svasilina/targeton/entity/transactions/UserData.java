package ua.svasilina.targeton.entity.transactions;

import android.app.ActionBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;

import ua.svasilina.targeton.entity.Account;
import ua.svasilina.targeton.utils.json.JsonAble;

import static ua.svasilina.targeton.utils.constants.Keys.ACCOUNTS;
import static ua.svasilina.targeton.utils.constants.Keys.CURRENCY;

public class UserData extends JsonAble {

    private final LinkedList<Account> accounts = new LinkedList<>();
    private final LinkedList<String> currencies = new LinkedList<>();

    public UserData (){}
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

    @Override
    public HashMap<String, Object> buildHashMap() {
        HashMap<String, Object> hashMap = new HashMap<>();
        JSONArray accountArray = new JSONArray();
        for (Account account : accounts){
            accountArray.put(account.buildJson());
        }
        hashMap.put(ACCOUNTS, accountArray);
        final JSONArray currencyArray = new JSONArray();
        for (String currency : currencies){
            currencyArray.put(currency);
        }
        hashMap.put(CURRENCY, currencyArray);
        return hashMap;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void addCurrency(String curr) {
        currencies.add(curr);
    }
}
