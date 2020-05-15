package utils;

import org.json.simple.JSONObject;

/**
 * Created by szpt_user045 on 17.02.2020.
 */
public abstract class JsonAble {
    public JsonPool pool = JsonPool.getPool();
    public JSONObject editorJson() {return toJson();}
    public JSONObject shortJson(){
        return toJson();
    }
    public abstract JSONObject toJson();
}
