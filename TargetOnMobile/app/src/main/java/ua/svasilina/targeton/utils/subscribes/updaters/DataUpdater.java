package ua.svasilina.targeton.utils.subscribes.updaters;

import org.json.JSONException;
import org.json.JSONObject;

public interface DataUpdater {
    void update(JSONObject object) throws JSONException;
    void sort();
}
