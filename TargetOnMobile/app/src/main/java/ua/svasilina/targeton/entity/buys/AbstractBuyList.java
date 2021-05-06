package ua.svasilina.targeton.entity.buys;

import org.json.JSONException;
import org.json.JSONObject;

import static ua.svasilina.targeton.utils.constants.Keys.ID;
import static ua.svasilina.targeton.utils.constants.Keys.TITLE;

public abstract class AbstractBuyList {
    private final int id;
    private String title;

    public AbstractBuyList(JSONObject object) throws JSONException {
        id = object.getInt(ID);
        update(object);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    private void update(JSONObject object) throws JSONException {
        title = object.getString(TITLE);
    }

}
