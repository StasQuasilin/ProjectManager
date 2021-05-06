package ua.svasilina.targeton.entity.buys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

import static ua.svasilina.targeton.utils.constants.Keys.ITEMS;

public class FullBuyList extends AbstractBuyList{
    private final LinkedList<BuyListItem> items = new LinkedList<>();
    public FullBuyList(JSONObject object) throws JSONException {
        super(object);
        updateItems(object);
    }

    private void updateItems(JSONObject object) throws JSONException {
        final JSONArray jsonArray = object.getJSONArray(ITEMS);
        for (int i = 0; i < jsonArray.length(); i++){
            final JSONObject jsonObject = jsonArray.getJSONObject(i);
            items.add(new BuyListItem(jsonObject));
        }
    }

    public LinkedList<BuyListItem> getItems() {
        return items;
    }
}
