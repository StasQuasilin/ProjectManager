package ua.svasilina.targeton.entity.buys;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import static ua.svasilina.targeton.utils.constants.Keys.COUNT;
import static ua.svasilina.targeton.utils.constants.Keys.ID;
import static ua.svasilina.targeton.utils.constants.Keys.PRICE;
import static ua.svasilina.targeton.utils.constants.Keys.TITLE;

public class BuyListItem {
    private int id;
    private String name;
    private int price;
    private int count;

    public BuyListItem(){
        id = -1;
    }

    public BuyListItem(JSONObject jsonObject) throws JSONException {
        id = jsonObject.getInt(ID);
        name = jsonObject.getString(TITLE);
        price = jsonObject.getInt(PRICE);
        count = jsonObject.getInt(COUNT);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
