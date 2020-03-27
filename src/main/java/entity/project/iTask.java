package entity.project;

import org.json.simple.JSONArray;
import utils.JsonAble;

public abstract class iTask extends JsonAble {

    iTask parent;

    public iTask getParent() {
        return parent;
    }

    public void setParent(iTask parent) {
        this.parent = parent;
    }

    public JSONArray buildPath() {
        JSONArray array = pool.getArray();
        if (parent != null){
            array.add(parent.shortJson());
            array.addAll(parent.buildPath());
        }
        return array;
    }
}
