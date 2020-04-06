package api.kanban;

import constants.API;
import constants.Keys;
import controllers.ServletAPI;
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

@WebServlet(API.TIMER_STOP)
public class StopTimerAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            TimeLog log = dao.getObjectById(TimeLog.class, body.get(ID));
            log.setEnd(Timestamp.valueOf(LocalDateTime.now()));
            dao.save(log);
            JSONObject json = new SuccessAnswer(Keys.RESULT, log.toJson()).toJson();
            write(resp, json.toJSONString());
            pool.put(json);
        }
    }
}
