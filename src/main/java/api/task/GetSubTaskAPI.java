package api.task;

import constants.API;
import constants.Keys;
import controllers.ServletAPI;
import entity.TreeItem;
import entity.task.Task;
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
            Object parentId = null;
            if (body.containsKey(PARENT)){
                parentId = body.get(PARENT);
            }
            int id = -1;
            if (body.containsKey(ID)){
                id = Integer.parseInt(String.valueOf(body.get(ID)));
            }
            for (Task task : dao.getTaskByUserAndParent(getUser(req), parentId)){
                if (task.getId() != id) {
                    array.add(task.toJson());
                }
            }

            SuccessAnswer answer = new SuccessAnswer(RESULT, array);
            //todo do something
            Task parentTask = dao.getObjectById(Task.class, parentId);
            if (parentTask != null && parentTask.getParent() == null){
                TreeItem treeItem = dao.getObjectById(TreeItem.class, parentId);
                answer.add(Keys.TREE, treeItem.toJson());
            }

            JSONObject json = answer.toJson();
            write(resp, json.toJSONString());
            pool.put(json);
        }
    }
}
