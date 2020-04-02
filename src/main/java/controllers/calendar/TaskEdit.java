package controllers.calendar;

import constants.API;
import constants.Branches;
import controllers.IModal;
import entity.calendar.CalendarItem;
import entity.user.User;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

/**
 * Created by szpt_user045 on 18.02.2020.
 */
@WebServlet(Branches.CALENDAR_EDIT)
public class TaskEdit extends IModal {
    private static final String _CONTENT = "/pages/calendar/taskEdit.jsp";
    private static final String _TITLE = "title.task.edit";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            CalendarItem calendarItem = dao.getObjectById(CalendarItem.class, body.get(ID));
            req.setAttribute(TASK, calendarItem);

            if (calendarItem == null && body.containsKey(TIME)){
                Date date = Date.valueOf(String.valueOf(body.get(DATE)));
                req.setAttribute(DATE, date);
                req.setAttribute(TIME, body.get(TIME));
            }
        }

        User user = getUser(req);
        req.setAttribute(PARENTS, dao.getParents(user));
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(PAGE_CONTENT, _CONTENT);
        req.setAttribute(SAVE, API.TASK_EDIT);
        show(req, resp);
    }
}
