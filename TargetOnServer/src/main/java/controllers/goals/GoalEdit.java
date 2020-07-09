package controllers.goals;

import constants.ApiLinks;
import constants.UrlLinks;
import controllers.ModalWindow;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static constants.Keys.*;

@WebServlet(UrlLinks.GOAL_EDIT)
public class GoalEdit extends ModalWindow {

    private static final String _CONTENT = "/pages/goals/goalEdit.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(SAVE, ApiLinks.GOAL_SAVE);
        show(req, resp);
    }
}
