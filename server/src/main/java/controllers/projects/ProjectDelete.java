package controllers.projects;

import constants.API;
import constants.Branches;
import controllers.IModal;
import entity.project.Project;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 02.03.2020.
 */
@WebServlet(Branches.PROJECT_DELETE)
public class ProjectDelete extends IModal {
    private static final String _TITLE = "title.project.delete";
    private static final String _CONTENT = "/pages/projects/projectDelete.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            Project project = dao.getObjectById(Project.class, body.get(ID));
            req.setAttribute(PROJECT, project);
            req.setAttribute(TITLE, _TITLE);
            req.setAttribute(PAGE_CONTENT, _CONTENT);
            req.setAttribute(DELETE, API.PROJECT_DELETE);
            show(req, resp);
        }
    }
}
