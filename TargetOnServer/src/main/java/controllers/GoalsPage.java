package controllers;

import constants.UrlLinks;
import subscribe.Subscribe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.CONTENT;
import static constants.Keys.SUBSCRIBE;

@WebServlet(UrlLinks.GOALS)
public class GoalsPage extends Page{
    private static final String PAGE = "/pages/goals/goalsList.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(CONTENT, PAGE);
        req.setAttribute(SUBSCRIBE, Subscribe.goal);
        show(req, resp);
    }
}
