package controllers.api.tree;

import constants.ApiLinks;
import controllers.api.API;
import entity.task.Task;
import entity.task.TimeLog;
import entity.user.User;
import subscribe.Subscribe;
import utils.Updater;
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
    private final Updater updater = new Updater();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if(body != null){
            System.out.println(body);
            TimeLog timeLog;
            if (body.containKey(ID)){
                timeLog = taskDAO.getTimeLog(body.get(ID));
            } else if (body.containKey(TASK)){
                final User user = getUser(req);
                timeLog = new TimeLog();
                final Task task = taskDAO.getTask(body.get(TASK));
                timeLog.setTask(task);
                Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
                timeLog.setBegin(timestamp);
                timeLog.setOwner(user);
                taskDAO.saveTimeLog(timeLog);
                final SuccessAnswer successAnswer = new SuccessAnswer();
                successAnswer.addAttribute(ID, timeLog.getId());
                successAnswer.addAttribute(BEGIN, timestamp.toString());
                write(resp, successAnswer);
                updater.update(Subscribe.timer, timeLog, user);
            }
        } else {
            write(resp, SUCCESS_ANSWER);
        }

    }
}
