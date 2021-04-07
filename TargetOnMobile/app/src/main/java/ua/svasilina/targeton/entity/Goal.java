package ua.svasilina.targeton.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import ua.svasilina.targeton.utils.constants.Keys;

public class Goal {
    private int id;
    private String title;
    private Calendar begin;
    private Calendar end;
    private int budget;
    private TaskStatistic statistic;

    public Goal(JSONObject object) {
        try {
            id = object.getInt(Keys.ID);
            update(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public Calendar getBegin() {
        return begin;
    }

    public void setBegin(Calendar begin) {
        this.begin = begin;
    }

    public Calendar getEnd() {
        return end;
    }

    public void setEnd(Calendar end) {
        this.end = end;
    }

    public void update(JSONObject object) throws JSONException {
        title = object.getString(Keys.TITLE);

        if (object.has(Keys.BEGIN)){
            begin = Calendar.getInstance();
            java.sql.Date date = java.sql.Date.valueOf(object.getString(Keys.BEGIN));
            begin.setTimeInMillis(date.getTime());
        } else {
            begin = null;
        }

        if (object.has(Keys.END)){
            end = Calendar.getInstance();
            java.sql.Date date = java.sql.Date.valueOf(object.getString(Keys.END));
            end.setTimeInMillis(date.getTime());
        } else {
            end = null;
        }

        if (object.has(Keys.BUDGET)){
            budget = object.getInt(Keys.BUDGET);
        } else {
            budget = 0;
        }

        if(object.has(Keys.STATISTIC)) {
            statistic = new TaskStatistic((JSONObject) object.get(Keys.STATISTIC));
        }
        if(object.has(Keys.MEMBERS)){
            System.out.println(object.get(Keys.MEMBERS));
        }
    }

    public TaskStatistic getStatistic() {
        return statistic;
    }
}
