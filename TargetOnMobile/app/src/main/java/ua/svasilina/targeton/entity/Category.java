package ua.svasilina.targeton.entity;

import org.json.JSONException;
import org.json.JSONObject;

import static ua.svasilina.targeton.utils.constants.Keys.ID;
import static ua.svasilina.targeton.utils.constants.Keys.TITLE;

public class Category {
    private int id;
    private String title;

    public Category(JSONObject json) {
        try {
            id = json.getInt(ID);
            title = json.getString(TITLE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Category() {

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
}
