package utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

/**
 * Created by szpt_user045 on 17.02.2020.
 */
public class JsonPool {

    private static final JsonPool pool = new JsonPool();

    public static JsonPool getPool() {
        return pool;
    }

    private static final int poolSize = 10;

    private JsonPool() {
        for (int i = 0; i < poolSize; i++){
            objects.add(new JSONObject());
            arrays.add(new JSONArray());
        }
    }

    private final ArrayList<JSONObject> objects = new ArrayList<>();
    private final ArrayList<JSONArray> arrays = new ArrayList<>();

    public JSONObject getObject(){
        if (objects.size() == 0){
            return new JSONObject();
        }
        return objects.remove(0);
    }

    public JSONArray getArray(){
        if (arrays.size() == 0){
            return new JSONArray();
        }
        return arrays.remove(0);
    }

    public void put(JSONObject object){
        for (Object o : object.values()){
            if (o instanceof JSONObject){
                put((JSONObject) o);
            } else if (o instanceof JSONArray){
                put((JSONArray) o);
            }
        }
        object.clear();
        objects.add(object);
    }
    public void put(JSONArray array){
        for (Object o : array){
            if (o instanceof JSONObject){
                put((JSONObject) o);
            } else if (o instanceof JSONArray){
                put((JSONArray) o);
            }
        }

        array.clear();
        arrays.add(array);
    }
}
