package controllers.projects;

import constants.Branches;
import constants.Keys;
import controllers.IPage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by quasilin on 23.02.2019.
 */
@WebServlet(Branches.PROJECTS)
public class ProjectList extends IPage implements Keys {
    private static final String _TITLE = "projects.title";
    private static final String _CONTENT = "/pages/projects/projectList.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(PAGE_CONTENT, _CONTENT);
        req.setAttribute(EDIT, Branches.PROJECT_EDIT);

        showPage(req, resp);
    }
}
