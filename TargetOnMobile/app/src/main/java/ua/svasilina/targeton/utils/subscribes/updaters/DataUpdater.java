package ua.svasilina.targeton.utils.subscribes.updaters;

import org.json.JSONObject;

public interface DataUpdater {
    void update(JSONObject object);
    void sort();
}
