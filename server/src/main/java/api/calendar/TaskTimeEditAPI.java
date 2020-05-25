package api.calendar;

import constants.API;
import controllers.ServletAPI;
import entity.task.Task;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by szpt_user045 on 04.03.2020.
 */
@WebServlet(API.TASK_TIME_EDIT)
public class TaskTimeEditAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            Task task = dao.getObjectById(Task.class, body.get(TASK));
            int index = Integer.parseInt(String.valueOf(body.get(INDEX)));
            int length = Integer.parseInt(String.valueOf(body.get(LENGTH)));
            LocalDate now = LocalDate.now();
            Timestamp time = Timestamp.valueOf(LocalDateTime.of(now, LocalTime.of(index, 0)));
            dao.save(task);
            write(resp, SUCCESS);
        }
    }
}
