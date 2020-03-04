package api.calendar;

import constants.API;
import constants.Branches;
import controllers.ServletAPI;
import entity.project.Task;
import entity.user.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

/**
 * Created by szpt_user045 on 04.03.2020.
 */
@WebServlet(API.GET_CALENDAR_ITEMS)
public class CalendarItemsAPI extends ServletAPI{



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            User user = getUser(req);
            Date date = Date.valueOf(String.valueOf(body.get(DATE)));
            System.out.println(date);

            JSONArray array = pool.getArray();
            for (Task task : dao.getTaskByDate(user, date)){
                array.add(task.toJson());
            }
            System.out.println(array);
            write(resp, array.toJSONString());
            pool.put(array);
        }
    }
}
