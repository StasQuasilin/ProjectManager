package ua.svasilina.targeton.entity.goal;

import org.json.JSONException;
import org.json.JSONObject;

import ua.svasilina.targeton.utils.constants.Keys;

import static ua.svasilina.targeton.utils.constants.Keys.TITLE;

public class SimpleGoal {
    private int id;
    private int titleId;
    private String title;

    public SimpleGoal(JSONObject object) {
        try {
            id = object.getInt(Keys.ID);
            titleId = object.getInt(Keys.TITLE_ID);
            title = object.getString(TITLE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public int getTitleId() {
        return titleId;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}
