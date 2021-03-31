package controllers.api.tree;

import constants.ApiLinks;
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

import static constants.Keys.STATUS;
import static constants.Keys.TASK;

@WebServlet(ApiLinks.TASK_STATUS)
public class TaskStatusApi extends API {

    private final TaskDAO taskDAO = daoService.getTaskDAO();
    private final TaskUtil taskUtil = new TaskUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        Answer answer;
        if(body != null){
            System.out.println(body);
            final Task task = taskDAO.getTask(body.get(TASK));
            TaskStatus status = TaskStatus.valueOf(body.getString(STATUS));
            task.setStatus(status);
            taskDAO.saveTask(task);
            taskUtil.updateStatistic(task.getHeader());
            answer = new SuccessAnswer();
        } else {
            answer =EMPTY_BODY;
        }
        write(resp, answer);
    }
}