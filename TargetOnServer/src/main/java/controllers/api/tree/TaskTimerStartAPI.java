package controllers.api.tree;

import constants.ApiLinks;
import controllers.api.API;
import entity.task.Task;
import entity.task.TaskStatus;
import entity.task.TimeLog;
import entity.user.User;
import subscribe.Subscribe;
import utils.TaskUtil;
import utils.Updater;
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
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static constants.Keys.*;

@WebServlet(ApiLinks.TIMER_START)
public class TaskTimerStartAPI extends API {

    private final TaskDAO taskDAO = daoService.getTaskDAO();
    private final TaskUtil taskUtil = new TaskUtil();
    private final Updater updater = new Updater();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        Answer answer;
        if(body != null){

            final User user = getUser(req);
            TimeLog timeLog = taskDAO.getActiveTimeLog(user);
            if (timeLog != null){
                answer = new ErrorAnswer("User have active timer already");
            } else {
                timeLog = new TimeLog();
                final Task task = taskDAO.getTask(body.get(TASK));
                if (task != null) {
                    final TaskStatus status = task.getStatus();
                    if (status == TaskStatus.active || status == TaskStatus.progressing) {
                        timeLog.setHeader(task.getHeader());
                        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
                        timeLog.setBegin(timestamp);
                        timeLog.setOwner(user);
                        taskDAO.saveTimeLog(timeLog);
                        answer = new SuccessAnswer();
                        answer.addAttribute(ID, timeLog.getId());
                        answer.addAttribute(BEGIN, timestamp.toString());
                        updater.update(Subscribe.timer, timeLog, user);
                        task.setStatus(TaskStatus.progressing);
                        taskDAO.saveTask(task);
                        taskUtil.updateStatistic(task.getHeader());
                        taskUtil.addTaskDoer(task, user);
                    } else {
                        answer = new ErrorAnswer("Task status is " + task.getStatus().toString());
                    }
                } else {
                    answer = new ErrorAnswer("Attribute 'task' required!");
                }
            }
        } else {
            answer = EMPTY_BODY;
        }
        write(resp, answer);

    }
}
