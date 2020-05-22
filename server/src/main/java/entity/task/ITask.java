package entity.task;

import org.json.simple.JSONArray;
import utils.JsonAble;

public abstract class ITask extends JsonAble {
    private ITask parent;

    public void setParent(ITask parent) {
        this.parent = parent;
    }

    public JSONArray buildPath(){
        JSONArray array = pool.getArray();
        if (parent != null){
            array.add(parent.shortJson());
            array.addAll(parent.buildPath());
        }
        return array;
    }
}
