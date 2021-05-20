package controllers.api.tree;

import constants.ApiLinks;
import controllers.api.API;
import entity.finance.category.Header;
import entity.task.Task;
import entity.task.TaskDependency;
import entity.user.User;
import org.json.simple.JSONArray;
import utils.answers.Answer;
import utils.answers.ErrorAnswer;
import utils.answers.SuccessAnswer;
import utils.db.dao.category.CategoryDAO;
import utils.db.dao.daoService;
import utils.db.dao.tree.TaskDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static constants.Keys.*;

@WebServlet(ApiLinks.FIND_DEPENDENCY)
public class FindDependencyApi extends API {

    private final TaskDAO taskDAO = daoService.getTaskDAO();
    private final CategoryDAO categoryDAO = daoService.getCategoryDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        Answer answer;
        if(body != null){
            System.out.println(body);
            if (body.containKey(TASK)){
                final Task task = taskDAO.getTask(body.get(TASK));
                final User user = getUser(req);
                final String key = body.getString(KEY);
                JSONArray array = new JSONArray();
                for (Header header : categoryDAO.findCategory(key, user, null)){
                    final Task taskByHeader = taskDAO.getTaskByHeader(header);
                    if(!task.equals(taskByHeader)) {
                        boolean add = true;

                        if (taskByHeader != null) {
                            System.out.println(taskByHeader.getTitle());
                            for (TaskDependency d : taskDAO.getPrincipal(taskByHeader)) {
                                if (d.getDependency().equals(task)) {
                                    System.out.println("\tDepend from " + task.getTitle());
                                    add = false;
                                    break;
                                }
                            }
                            for (TaskDependency d : taskDAO.getDependency(taskByHeader)) {
                                if (d.getTask().equals(task)) {
                                    System.out.println("\tDepend to " + task.getTitle());
                                    add = false;
                                    break;
                                }
                            }
                        }
                        if (add) {
                            array.add(header.shortJson());
                        }
                    }
                }
                answer = new SuccessAnswer();
                answer.addAttribute(RESULT, array);
            } else {
                answer = new ErrorAnswer("Task required");
            }
        } else {
            answer = new ErrorAnswer("No req body");
        }

        write(resp, answer);
    }
}
