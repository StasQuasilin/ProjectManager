package utils;

import entity.Project;
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

    public static JSONObject toJson(Project project) {
        JSONObject json = new JSONObject();
        json.put("id", project.getId());
        json.put("date", project.getDate().toString());
        json.put("description", project.getDescription());
        return json;
    }
}
