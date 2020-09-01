package ua.svasilina.targeton.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.Calendar;

import static ua.svasilina.targeton.utils.constants.Keys.ACCOUNT_FROM;
import static ua.svasilina.targeton.utils.constants.Keys.ACCOUNT_TO;
import static ua.svasilina.targeton.utils.constants.Keys.AMOUNT;
import static ua.svasilina.targeton.utils.constants.Keys.CATEGORY;
import static ua.svasilina.targeton.utils.constants.Keys.CURRENCY;
import static ua.svasilina.targeton.utils.constants.Keys.DATE;
import static ua.svasilina.targeton.utils.constants.Keys.RATE;


public class Transaction implements Comparable<Transaction>{
    private Calendar date;
    private Category category;
    private double amount;
    private double rate;
    private String currency;
    private Account accountFrom;
    private Account accountTo;

    public Transaction(JSONObject json) {
        System.out.println(json);
        try {
            date = Calendar.getInstance();

            final Date d = Date.valueOf(json.getString(DATE));
            date.setTimeInMillis(d.getTime());

            category = new Category(json.getJSONObject(CATEGORY));
            amount = json.getDouble(AMOUNT);
            rate = json.getDouble(RATE);
            currency = json.getString(CURRENCY);
            if (json.has(ACCOUNT_FROM)){
                accountFrom = new Account(json.getJSONObject(ACCOUNT_FROM));
            }
            if (json.has(ACCOUNT_TO)){
                accountTo = new Account(json.getJSONObject(ACCOUNT_TO));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Transaction() {

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

    @Override
    public int compareTo(Transaction o) {
        return date.compareTo(o.getDate());
    }
}
