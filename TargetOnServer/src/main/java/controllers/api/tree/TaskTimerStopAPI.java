package controllers.api.tree;

import constants.ApiLinks;
import controllers.api.API;
import entity.task.Task;
import entity.task.TaskStatus;
import entity.task.TimeLog;
import subscribe.Subscribe;
import utils.TaskUtil;
import utils.Updater;
import utils.answers.Answer;
import utils.db.dao.daoService;
import utils.db.dao.tree.TaskDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static constants.Keys.*;

@WebServlet(ApiLinks.TIMER_STOP)
public class TaskTimerStopAPI extends API {

    private final TaskDAO taskDAO = daoService.getTaskDAO();
    private final TaskUtil taskUtil = new TaskUtil();
    private final Updater updater = new Updater();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        Answer answer;
        if (body != null){
            final TimeLog timeLog = taskDAO.getTimeLog(body.get(ID));
            Timestamp end = Timestamp.valueOf(LocalDateTime.now());
            timeLog.setEnd(end);
            final long length = timeLog.getLength();
            if(length > 0){
                taskDAO.saveTimeLog(timeLog);
                taskUtil.calculateSpendTime(timeLog.getHeader());
            } else {
                taskDAO.removeTimeLog(timeLog);
            }

            updater.remove(Subscribe.timer, timeLog.getId(), timeLog.getOwner());
            answer = SUCCESS_ANSWER;
            final Task task = taskDAO.getTaskByHeader(timeLog.getHeader());
            task.setStatus(TaskStatus.active);
            taskDAO.saveTask(task);
            taskUtil.updateStatistic(task.getHeader());

        } else {
            answer = EMPTY_BODY;
        }
        write(resp, answer);
    }
}
