package utils;

import org.json.simple.JSONObject;

/**
 * Created by szpt_user045 on 17.02.2020.
 */
public interface JsonAble {
    JsonPool pool = JsonPool.getPool();
    JSONObject toJson();
}
