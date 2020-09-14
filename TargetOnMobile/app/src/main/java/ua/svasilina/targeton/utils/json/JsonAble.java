package ua.svasilina.targeton.utils.json;

import org.json.JSONObject;

import java.util.HashMap;

public abstract class JsonAble {
    public abstract HashMap<String, Object> buildHashMap();
    public JSONObject buildJson(){
        return new JSONObject(buildHashMap());
    }
}
