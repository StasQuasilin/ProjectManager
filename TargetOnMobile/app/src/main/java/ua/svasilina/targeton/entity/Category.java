package ua.svasilina.targeton.entity;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import ua.svasilina.targeton.utils.json.JsonAble;

import static ua.svasilina.targeton.utils.constants.Keys.ID;
import static ua.svasilina.targeton.utils.constants.Keys.TITLE;

public class Category extends JsonAble {
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

    @NonNull
    @Override
    public String toString() {
        return title;
    }

    @Override
    public HashMap<String, Object> buildHashMap() {
        final HashMap<String, Object> map = new HashMap<>();
        map.put(ID, id);
        map.put(TITLE, title);
        return map;
    }
}
