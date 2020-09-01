package ua.svasilina.targeton.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import static ua.svasilina.targeton.utils.constants.Keys.AMOUNT;
import static ua.svasilina.targeton.utils.constants.Keys.CURRENCY;
import static ua.svasilina.targeton.utils.constants.Keys.ID;
import static ua.svasilina.targeton.utils.constants.Keys.LEFT_BRACE;
import static ua.svasilina.targeton.utils.constants.Keys.RIGHT_BRACE;
import static ua.svasilina.targeton.utils.constants.Keys.SPACE;
import static ua.svasilina.targeton.utils.constants.Keys.SUM;
import static ua.svasilina.targeton.utils.constants.Keys.TITLE;

public class Account {
    private int id;
    private String title;
    private double amount;
    private String currency;

    public Account(JSONObject json) {

        try {
            id = json.getInt(ID);
            title = json.getString(TITLE);
            if (json.has(CURRENCY)){
                currency = json.getString(CURRENCY);
            }
            if (json.has(SUM)){
                amount = json.getDouble(SUM);
            }
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
}
