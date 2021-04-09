package ua.svasilina.targeton.entity.tree;

import org.json.JSONException;
import org.json.JSONObject;

import ua.svasilina.targeton.utils.constants.Keys;

public class SimpleTask {

    private int id;
    private int headerId;
    private String title;

    public SimpleTask(int id, int header, String title){
        this.id = id;
        this.headerId = header;
        this.title = title;
    }

    public SimpleTask(JSONObject jsonObject) throws JSONException {
        id = jsonObject.getInt(Keys.ID);
        headerId = jsonObject.getInt(Keys.HEADER);
        title = jsonObject.getString(Keys.TITLE);
    }

    public String getTitle() {
        return title;
    }

    public int getHeaderId() {
        return headerId;
    }
}
