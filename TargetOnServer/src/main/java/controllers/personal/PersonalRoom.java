package controllers.personal;

import constants.UrlLinks;
import controllers.Page;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.CONTENT;
import static constants.Keys.TITLE;

@WebServlet(UrlLinks.PERSONAL)
public class PersonalRoom extends Page {
    private static final String _TITLE = "title.personal";
    private static final String _CONTENT = "/pages/personal/personalRoom.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(CONTENT, _CONTENT);
        show(req, resp);
    }
}
