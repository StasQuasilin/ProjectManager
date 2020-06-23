package utils.json;

import org.json.simple.JSONObject;

public class JsonObject {

    final JSONObject json;

    public JsonObject(Object parsed) {
        json = (JSONObject) parsed;
    }

    public String getString(String key){
        return String.valueOf(json.get(key));
    }

    public boolean containKey(String key) {
        return json.containsKey(key);
    }

    @Override
    public String toString() {
        return json.toJSONString();
    }
}
