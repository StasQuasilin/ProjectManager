package ua.svasilina.targeton.entity.goal;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import ua.svasilina.targeton.entity.TaskStatistic;
import ua.svasilina.targeton.entity.goal.SimpleGoal;
import ua.svasilina.targeton.utils.constants.Keys;

public class Goal extends SimpleGoal {
    private Calendar begin;
    private Calendar end;
    private int budget;
    private TaskStatistic statistic;
    private int members;

    public Goal(JSONObject object) {
        super(object);
        try {

            update(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    public int getMembers() {
        return members;
    }

    public void update(JSONObject object) throws JSONException {
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
            members = object.getJSONArray(Keys.MEMBERS).length();
        }
    }

    public TaskStatistic getStatistic() {
        return statistic;
    }
}
