package ua.svasilina.targeton.entity;

import org.json.JSONException;
import org.json.JSONObject;

import ua.svasilina.targeton.utils.constants.Keys;

public class TaskStatistic {
    private int active;
    private int progressing;
    private int done;
    private int other;
    private float plus;
    private float minus;
    private int spendTime;

    public TaskStatistic(JSONObject json) throws JSONException {
        active = json.getInt(Keys.ACTIVE);
        progressing = json.getInt(Keys.PROGRESSING);
        done = json.getInt(Keys.DONE);
        other = json.getInt(Keys.OTHER);

    }

    public int getActive() {
        return active;
    }

    public int getProgressing() {
        return progressing;
    }

    public int getDone() {
        return done;
    }

    public int getOther() {
        return other;
    }
}
