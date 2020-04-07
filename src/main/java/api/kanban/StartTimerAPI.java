package api.kanban;

import constants.API;
import constants.Keys;
import controllers.ServletAPI;
import entity.task.Task;
import entity.task.TimeLog;
import org.json.simple.JSONObject;
import services.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@WebServlet(API.TIMER_START)
public class StartTimerAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            Task task = dao.getObjectById(Task.class, body.get(TASK));
            TimeLog timeLog = dao.getActiveTimeLog(task);
            if (timeLog == null){
                timeLog = new TimeLog();
                timeLog.setDoer(getUser(req));
                timeLog.setTask(task);
            }
            timeLog.setBegin(Timestamp.valueOf(LocalDateTime.now()));
            dao.save(timeLog);
            JSONObject json = new SuccessAnswer(Keys.RESULT, timeLog.toJson()).toJson();
            write(resp, json.toJSONString());
            pool.put(json);
        }
    }
}
