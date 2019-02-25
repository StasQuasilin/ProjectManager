package utils;

import entity.Budget;
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

    public static JSONObject toJson(Project project, int active, int done, int canceled) {
        JSONObject json = new JSONObject();
        json.put("id", project.getId());
        json.put("title", project.getTitle());
        json.put("beginDate", project.getBeginDate().toString());
        json.put("completeDate", project.getCompleteDate().toString());
        json.put("description", project.getDescription());
        json.put("active", active);
        json.put("done", done);
        json.put("canceled", canceled);
        json.put("progress", active + done > 0 ? 1f * active / (active + done) : 0);
        if (project.getBudget() != null){
            json.put("budget", toJson(project.getBudget()));
        }
        return json;
    }

    private static JSONObject toJson(Budget budget) {
        JSONObject json = new JSONObject();
        json.put("id", budget.getId());
        json.put("title", budget.getTitle());
        json.put("sum", budget.getBudgetSum());
        json.put("currency", budget.getCurrency());

        return json;
    }
}
