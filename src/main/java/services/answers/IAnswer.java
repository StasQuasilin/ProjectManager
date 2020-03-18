package services.answers;

import constants.Keys;
import org.json.simple.JSONObject;
import utils.JsonAble;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by szpt_user045 on 19.02.2019.
 */
public abstract class IAnswer extends JsonAble implements Keys {

    public abstract String type();
    private HashMap<String, Object> params;

    public IAnswer() {
        params = new HashMap<>();
    }

    public void add(String key, Object value){
        params.put(key, value);
    }

    public HashMap<String, Object> getParams() {
        return params;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = pool.getObject();
        object.put(STATUS, type());
        for (Map.Entry<String, Object> entry : params.entrySet()){
            object.put(entry.getKey(), entry.getValue());
        }
        return object;
    }
}
