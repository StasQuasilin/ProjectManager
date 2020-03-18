package api.task;

import constants.API;
import controllers.ServletAPI;
import entity.project.Task;
import entity.project.TaskStatus;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import services.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 27.02.2020.
 */
@WebServlet(API.GET_SUB_TASK)
public class GetSubTaskAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            JSONArray array = pool.getArray();
            TaskStatus status = TaskStatus.valueOf(String.valueOf(body.get(STATUS)));
            Object parent = null;
            if (body.containsKey(PARENT)){
                parent = body.get(PARENT);
            }
            for (Task task : dao.getTasksByParent(parent, status)){
                array.add(task.toJson());
            }
            JSONObject json = new SuccessAnswer(RESULT, array).toJson();
            write(resp, json.toJSONString());
            pool.put(json);
        }
    }
}
