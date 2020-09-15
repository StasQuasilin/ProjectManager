package ua.svasilina.targeton.entity.transactions;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;

import ua.svasilina.targeton.entity.Account;
import ua.svasilina.targeton.entity.Category;
import ua.svasilina.targeton.utils.builders.DateTimeBuilder;
import ua.svasilina.targeton.utils.constants.Constants;
import ua.svasilina.targeton.utils.json.JsonAble;

import static ua.svasilina.targeton.utils.constants.Keys.TYPE;
import static ua.svasilina.targeton.utils.constants.Keys.ACCOUNT_FROM;
import static ua.svasilina.targeton.utils.constants.Keys.ACCOUNT_TO;
import static ua.svasilina.targeton.utils.constants.Keys.AMOUNT;
import static ua.svasilina.targeton.utils.constants.Keys.CATEGORY;
import static ua.svasilina.targeton.utils.constants.Keys.CURRENCY;
import static ua.svasilina.targeton.utils.constants.Keys.DATE;
import static ua.svasilina.targeton.utils.constants.Keys.ID;
import static ua.svasilina.targeton.utils.constants.Keys.RATE;


public class Transaction extends JsonAble implements Comparable<Transaction>{
    private int id;
    private Calendar date;
    private Category category;
    private double amount;
    private double rate;
    private String currency;
    private Account accountFrom;
    private Account accountTo;
    private TransactionType type = TransactionType.spending;

    public Transaction(JSONObject json) {
        System.out.println("!!" + json);
        try {
            id = json.getInt(ID);
            date = Calendar.getInstance();

            update(json);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Transaction() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Calendar getDate() {
        return date;
    }
    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getTitle() {
        return category.getTitle();
    }

    public void setTitle(String title) {
        category.setTitle(title);
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Account getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(Account accountFrom) {
        this.accountFrom = accountFrom;
    }

    public Account getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Account accountTo) {
        this.accountTo = accountTo;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    @Override
    public int compareTo(Transaction o) {
        return date.compareTo(o.getDate());
    }


    @Override
    public HashMap<String, Object> buildHashMap() {
        final HashMap<String, Object> hashMap = new HashMap<>();
        final DateTimeBuilder builder = new DateTimeBuilder(Constants.ISO_PATTERN);
        hashMap.put(ID, id);
        hashMap.put(DATE, builder.build(date));
        hashMap.put(CATEGORY, category.buildJson());
        hashMap.put(AMOUNT, amount);
        hashMap.put(CURRENCY, currency);
        hashMap.put(RATE, rate);
        
        if (accountFrom != null){
            hashMap.put(ACCOUNT_FROM, accountFrom.getId());
        }
        if (accountTo != null){
            hashMap.put(ACCOUNT_TO, accountTo.getId());
        }
        hashMap.put(TYPE, type.toString());
        return hashMap;
    }

    public void update(JSONObject json) throws JSONException {
        final Date d = Date.valueOf(json.getString(DATE));
        date.setTimeInMillis(d.getTime());
        category = new Category(json.getJSONObject(CATEGORY));
        amount = json.getDouble(AMOUNT);
        rate = json.getDouble(RATE);
        currency = json.getString(CURRENCY);
        if (json.has(ACCOUNT_FROM)){
            accountFrom = new Account(json.getJSONObject(ACCOUNT_FROM));
        } else {
            accountFrom = null;
        }
        if (json.has(ACCOUNT_TO)){
            accountTo = new Account(json.getJSONObject(ACCOUNT_TO));
        } else {
            accountTo = null;
        }

        type = TransactionType.valueOf(json.getString(TYPE));
    }
}
