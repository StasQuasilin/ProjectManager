package ua.svasilina.targeton.entity.transactions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ua.svasilina.targeton.entity.Category;
import ua.svasilina.targeton.utils.constants.Keys;

import static ua.svasilina.targeton.utils.constants.Keys.HEADER;
import static ua.svasilina.targeton.utils.constants.Keys.ID;
import static ua.svasilina.targeton.utils.constants.Keys.TITLE;

public class TransactionDetail {
    private int id;
    private Category category;

    private float count;
    private float price;

    public TransactionDetail(){

    }

    public TransactionDetail(JSONObject object) throws JSONException {
        id = object.getInt(ID);
        category = new Category();
        category.setId(object.getInt(HEADER));
        category.setTitle(object.getString(TITLE));
        category.buildPath(object.getJSONArray(Keys.PATH));
        count = Float.parseFloat(object.getString(Keys.AMOUNT));
        price = Float.parseFloat(object.getString(Keys.PRICE));
    }

    public float getPrice() {
        return price;
    }

    public float getCount() {
        return count;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public String[] getPath() {
        return category.getPath();
    }
}
