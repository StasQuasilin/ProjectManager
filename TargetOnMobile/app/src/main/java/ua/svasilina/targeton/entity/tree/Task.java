package ua.svasilina.targeton.entity.tree;

import org.json.JSONException;
import org.json.JSONObject;

import ua.svasilina.targeton.utils.constants.Keys;

public class Task extends SimpleTask{
    private final TaskStatus status;
    private final int childrenCount;
    private boolean doneIf;
    public Task(JSONObject jsonObject) throws JSONException {
        super(jsonObject);
        System.out.println(jsonObject);
        status = TaskStatus.valueOf(jsonObject.getString(Keys.STATUS));
        if(jsonObject.has(Keys.CHILDREN)) {
            childrenCount = jsonObject.getJSONArray(Keys.CHILDREN).length();
        } else {
            childrenCount =0;
        }
        doneIf = jsonObject.getBoolean(Keys.DONE_IF);
    }

    public TaskStatus getStatus() {
        return status;
    }

    public int getChildrenCount() {
        return childrenCount;
    }

    public boolean isDoneIf() {
        return doneIf;
    }
}
