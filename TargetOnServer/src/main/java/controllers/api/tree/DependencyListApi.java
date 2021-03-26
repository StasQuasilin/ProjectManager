package controllers.api.tree;

import constants.ApiLinks;
import constants.Keys;
import controllers.api.API;
import entity.task.TaskDependency;
import org.json.simple.JSONArray;
import utils.answers.Answer;
import utils.answers.ErrorAnswer;
import utils.answers.SuccessAnswer;
import utils.db.dao.daoService;
import utils.db.dao.tree.TaskDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(ApiLinks.DEPENDENCY_LIST)
public class DependencyListApi extends API {

    private final TaskDAO taskDAO = daoService.getTaskDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        Answer answer;
        if (body != null){
            if(body.containKey(Keys.ID)){
                JSONArray array = new JSONArray();
                for (TaskDependency dependency : taskDAO.getDependency(body.get(Keys.ID))){
                    array.add(dependency.getDependency().shortJson());
                }
                answer = new SuccessAnswer();
                answer.addAttribute(Keys.RESULT, array);
            } else {
                answer = new ErrorAnswer("Task id required");
            }
        } else {
            answer = new ErrorAnswer("No req body");
        }
        write(resp, answer);
    }
}
