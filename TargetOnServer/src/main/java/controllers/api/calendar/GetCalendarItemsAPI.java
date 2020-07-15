package controllers.api.calendar;

import constants.ApiLinks;
import controllers.api.API;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(ApiLinks.GET_CALENDAR)
public class GetCalendarItemsAPI extends API {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
        }
    }
}
