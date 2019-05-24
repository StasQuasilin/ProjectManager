package controllers.projects;

import constants.API;
import constants.Links;
import controllers.IPage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by quasilin on 23.02.2019.
 */
@WebServlet(Links.PROJECTS)
public class ProjectList extends IPage {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "projects.title");
        req.setAttribute("pageContent", "/pages/projects/projectList.jsp");
        req.setAttribute("edit", Links.PROJECT_EDIT);
        req.setAttribute("update", API.PROJECT.LIST);

        showPage(req, resp);
    }
}
