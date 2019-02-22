package utils;

import org.json.simple.JSONObject;
import services.answers.IAnswer;

import java.util.Map;

/**
 * Created by szpt_user045 on 22.02.2019.
 */
public class JsonParser {
    public static JSONObject toJson(IAnswer answer) {
        JSONObject json = new JSONObject();

        json.put("status", answer.type());
        for (Map.Entry<String,String> entry : answer.getParams().entrySet()){
            json.put(entry.getKey(), entry.getValue());
        }

        return json;
    }
}
