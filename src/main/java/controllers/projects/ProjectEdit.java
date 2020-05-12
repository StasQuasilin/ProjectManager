package controllers.projects;

import constants.API;
import constants.Branches;
import controllers.IModal;
import entity.budget.BudgetSize;
import entity.project.Project;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by quasilin on 24.02.2019.
 */
@WebServlet(Branches.PROJECT_EDIT)
public class ProjectEdit extends IModal {

    private static final Logger log = Logger.getLogger(ProjectEdit.class);
    private static final String _EDIT_TITLE = "project.edit";
    private static final String _CREATE_TITLE = "project.create";
    private static final String _CONTENT = "/pages/projects/projectEdit.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            if (body.containsKey(ID)){
                req.setAttribute(PROJECT, dao.getObjectById(Project.class, body.get(ID)));
                req.setAttribute(TITLE, _EDIT_TITLE);
            } else {
                req.setAttribute(TITLE, _CREATE_TITLE);
            }
        }

        req.setAttribute(PAGE_CONTENT, _CONTENT);
        req.setAttribute(SAVE, API.PROJECT.SAVE);
        req.setAttribute(BUDGET_TYPES, BudgetSize.values());
        show(req, resp);
    }
}
