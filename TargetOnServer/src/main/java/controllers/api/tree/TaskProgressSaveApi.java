package controllers.api.tree;

import constants.ApiLinks;
import constants.Keys;
import controllers.api.API;
import entity.task.Task;
import entity.task.TaskStatus;
import utils.TaskUtil;
import utils.answers.Answer;
import utils.answers.SuccessAnswer;
import utils.db.dao.daoService;
import utils.db.dao.tree.TaskDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(ApiLinks.SAVE_TASK_PROGRESS)
public class TaskProgressSaveApi extends API {

    private final TaskDAO taskDAO = daoService.getTaskDAO();
    private final TaskUtil taskUtil = new TaskUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if(body != null){
            System.out.println(body);
            final Task task = taskDAO.getTask(body.get(Keys.TASK));
            float progress = body.getFloat(Keys.PROGRESS);
            task.setProgress(progress);
            final float target = task.getTarget();
            if (progress >= target){
                task.setStatus(TaskStatus.done);
            }
            taskDAO.saveTask(task);
            if (progress >= target){
                taskUtil.updateStatistic(task.getHeader());
            }
            Answer answer = new SuccessAnswer();
            answer.addAttribute(Keys.RESULT, progress);
            write(resp, answer);
        }
    }
}
