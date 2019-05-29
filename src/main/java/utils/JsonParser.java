package utils;

import entity.budget.Budget;
import entity.Project;
import entity.Task;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import services.answers.IAnswer;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        json.put("time", project.getTimeProgress());
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
        json.put("size", budget.getBudgetSize().toString());
        json.put("type", budget.getBudgetType().toString());

        return json;
    }

    public static JSONObject toJson(Task task) {
        JSONObject json = new JSONObject();
        json.put("id", task.getId());
        json.put("title", task.getTitle());
        json.put("status", task.getStatus());
        Task parent = task.getParent();
        if (parent != null){
            json.put("parent", parent.getId());
        } else {
            json.put("parent", parent);
        }

        return json;
    }

    public static JSONObject toJson(Task task, List<Task> tasks) {
        JSONObject json = new JSONObject();
        if (task != null) {
            json = toJson(task);
            json.put("path", toPathJson(task));
            json.put("tasks", toTaskJson(tasks));
            if (task.getParent() != null){
                return toParentTask(task.getParent(), json);
            }
        } else {
            json.put("tasks", toTaskJson(tasks));
        }

        return json;
    }

    private static JSONObject toPathJson(Task task) {
        JSONObject json = new JSONObject();

        return json;
    }

    static JSONObject toParentTask(Task parent, JSONObject child){
        JSONObject json = toJson(parent);
        json.put("child", child);

        if (parent.getParent()!= null) {
            return toParentTask(parent.getParent(), json);
        }

        return json;
    }

    public static void main(String[] args) {
        Task task1 = createTask(1, null);
        Task task2 = createTask(2, task1);
        Task task3 = createTask(3, task2);
        Task task4 = createTask(4, task3);

        List<Task> childs = new LinkedList<>();
        childs.add(createTask(1, task4));
        childs.add(createTask(2, task4));
        childs.add(createTask(3, task4));
        childs.add(createTask(4, task4));

//        System.out.println(prettyJson(toTaskJson(task4, childs).toJSONString()));
        System.out.println(prettyJson(toJson(null, childs).toJSONString()));

    }
    static Task createTask(int num, Task parent){
        Task task = new Task("Task #" + num);
        task.setId(num);
        task.setParent(parent);
        return task;
    }

    public static String prettyJson(String json){
        int tabLevel = 0;
        StringBuilder builder = new StringBuilder();
        for (char c : json.toCharArray()){
            if (c == '}' || c == ']'){
                tabLevel--;
                builder.append('\n');
                for (int i = 0; i < tabLevel; i++){
                    builder.append('\t');
                }
            }

            builder.append(c);
            if (c == '{' || c == '['){
                builder.append('\n');
                tabLevel++;
                for (int i = 0; i < tabLevel; i++){
                    builder.append('\t');
                }
            }

            if (c == ','){
                builder.append('\n');
                for (int i = 0; i < tabLevel; i++){
                    builder.append('\t');
                }
            }
        }
        return builder.toString();
    }


    private static JSONArray toTaskJson(List<Task> tasks) {
        return tasks.stream().map(JsonParser::toJson).collect(Collectors.toCollection(JSONArray::new));
    }

    public static JSONArray toJson(List<Budget> budgets) {
        JSONArray array = new JSONArray();
        for (Budget budget : budgets) {
            array.add(toJson(budget));
        }
        return array;
    }
}
