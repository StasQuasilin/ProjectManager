package ua.svasilina.targeton.utils.subscribes.updaters;

import org.json.JSONObject;

public abstract class DataUpdater {
    public abstract void update(JSONObject object);

    public abstract void sort();
}
