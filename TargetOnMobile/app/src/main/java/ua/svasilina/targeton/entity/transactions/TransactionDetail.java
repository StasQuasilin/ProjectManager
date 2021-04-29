package ua.svasilina.targeton.entity.transactions;

import org.json.JSONException;
import org.json.JSONObject;

import ua.svasilina.targeton.entity.Category;
import ua.svasilina.targeton.utils.constants.Keys;

import static ua.svasilina.targeton.utils.constants.Keys.ID;

public class TransactionDetail {
    private int id;
    private Category category;
    private float count;
    private float price;

    public TransactionDetail(JSONObject object) throws JSONException {
        id = object.getInt(ID);
        count = Float.parseFloat(object.getString(Keys.AMOUNT));
        price = Float.parseFloat(object.getString(Keys.PRICE));
    }

    public float getPrice() {
        return price;
    }

    public float getCount() {
        return count;
    }
}
