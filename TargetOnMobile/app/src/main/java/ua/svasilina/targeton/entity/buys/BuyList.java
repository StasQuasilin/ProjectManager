package ua.svasilina.targeton.entity.buys;

import org.json.JSONException;
import org.json.JSONObject;

import ua.svasilina.targeton.utils.constants.Keys;

import static ua.svasilina.targeton.utils.constants.Keys.COST;
import static ua.svasilina.targeton.utils.constants.Keys.ID;
import static ua.svasilina.targeton.utils.constants.Keys.TITLE;

public class BuyList extends AbstractBuyList {

    private int items;
    private int cost;
    private int spend;

    public BuyList(JSONObject object) throws JSONException {
        super(object);
        updateValues(object);
    }

    public int getItems() {
        return items;
    }

    public int getCost() {
        return cost;
    }

    public int getSpend() {
        return spend;
    }

    public void updateValues(JSONObject object) throws JSONException {
        items = object.getJSONArray(Keys.ITEMS).length();
        if(object.has(Keys.COST)) {
            final JSONObject costObj = object.getJSONObject(Keys.COST);
            cost = costObj.getInt(COST);
            spend = costObj.getInt(Keys.SPEND);
        }else {
            cost = 0;
            spend = 0;
        }

    }
}
