package controllers.api.calendar;

import constants.ApiLinks;
import constants.Keys;
import controllers.api.API;
import entity.calendar.CalendarItem;
import entity.task.Task;
import entity.user.User;
import org.json.simple.JSONArray;
import utils.db.dao.daoService;
import utils.db.dao.tree.TaskDAO;
import utils.json.JsonObject;
import utils.savers.CalendarSaver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

@WebServlet(ApiLinks.SAVE_RANDOM_TASK)
public class SaveRandomTaskAPI extends API {

    private final TaskDAO taskDAO = daoService.getTaskDAO();
    private final CalendarSaver calendarSaver = new CalendarSaver();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if(body != null){
            System.out.println(body);
            Date date = null;

            if (body.containKey(Keys.DATE)){
                date = body.getDate(Keys.DATE);
            }
            if (date == null)            {
                date = Date.valueOf(LocalDate.now());
            }
            final User user = getUser(req);
            for (Object o : body.getJsonArray(Keys.RESULT)){
                Task task = taskDAO.getTask(o);
                CalendarItem calendarItem = new CalendarItem();
                calendarItem.setDate(date);
                calendarItem.setHeader(task.getHeader());
                calendarItem.setOwner(user);
                calendarSaver.save(calendarItem);
            }
            write(resp, SUCCESS_ANSWER);
        }
    }
}
