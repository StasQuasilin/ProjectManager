package controllers.calendar;

import constants.ApiLinks;
import constants.UrlLinks;
import controllers.ModalWindow;
import entity.calendar.CalendarItem;
import entity.finance.category.Header;
import utils.db.dao.calendar.CalendarDAO;
import utils.db.dao.category.CategoryDAO;
import utils.db.dao.daoService;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.*;

@WebServlet(UrlLinks.CALENDAR_EDIT)
public class CalendarEdit extends ModalWindow {
    private static final String _TITLE = "title.calendar.edit";
    private static final String _CONTENT = "/pages/calendar/calendarEdit.jsp";
    private final CalendarDAO calendarDAO = daoService.getCalendarDAO();
    private final CategoryDAO categoryDAO = daoService.getCategoryDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            CalendarItem item = calendarDAO.getCalendarItem(body.get(ID));
            if (item == null && body.containKey(CATEGORY)){
                final Header category = categoryDAO.getCategory(body.get(CATEGORY));
                item = new CalendarItem();
                item.setHeader(category);
            }
            req.setAttribute(ITEM, item);
        }
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(SAVE, ApiLinks.CALENDAR_EDIT);
        req.setAttribute(FIND_CATEGORY, ApiLinks.FIND_CATEGORY);

        show(req, resp);
    }
}
