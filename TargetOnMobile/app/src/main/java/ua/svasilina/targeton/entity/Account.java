package ua.svasilina.targeton.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import ua.svasilina.targeton.utils.constants.Keys;
import ua.svasilina.targeton.utils.json.JsonAble;

import static ua.svasilina.targeton.utils.constants.Keys.AMOUNT;
import static ua.svasilina.targeton.utils.constants.Keys.CURRENCY;
import static ua.svasilina.targeton.utils.constants.Keys.ID;
import static ua.svasilina.targeton.utils.constants.Keys.LEFT_BRACE;
import static ua.svasilina.targeton.utils.constants.Keys.LIMIT;
import static ua.svasilina.targeton.utils.constants.Keys.RIGHT_BRACE;
import static ua.svasilina.targeton.utils.constants.Keys.SPACE;
import static ua.svasilina.targeton.utils.constants.Keys.SUM;
import static ua.svasilina.targeton.utils.constants.Keys.TITLE;

public class Account extends JsonAble {
    private int id;
    private String title;
    private double amount;
    private String currency;
    private int limit;

    public Account(JSONObject json) {

        try {
            id = json.getInt(ID);
            update(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @NonNull
    @Override
    public String toString() {
        return title + SPACE + LEFT_BRACE + SPACE + amount + SPACE + currency + SPACE + RIGHT_BRACE;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        assert obj != null;
        return obj.getClass().equals(getClass()) && hashCode() == obj.hashCode();
    }

    @Override
    public HashMap<String, Object> buildHashMap() {
        final HashMap<String, Object> map = new HashMap<>();
        return map;
    }

    public void update(JSONObject json) throws JSONException {

        if (json.has(CURRENCY)){
            currency = json.getString(CURRENCY);
        }
        if (json.has(SUM)){
            amount = json.getDouble(SUM);
        }
        if (json.has(Keys.LIMIT)){
            limit = json.getInt(LIMIT);
        }
    }
}
