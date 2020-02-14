package controllers.projects;

import constants.API;
import constants.Branches;
import controllers.IModal;
import entity.budget.Budget;
import entity.budget.BudgetSize;
import entity.project.Project;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import services.hibernate.Hibernator;

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
    public static final Hibernator hibernator = Hibernator.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            if (body.containsKey(ID)){
                long id = (long) body.remove("id");
                req.setAttribute("project",hibernator.get(Project.class, "id", id));
                req.setAttribute("title", "project.edit");
            } else {
                req.setAttribute("title", "project.create");
            }
        }

        req.setAttribute(PAGE_CONTENT, "/pages/projects/projectEdit.jsp");
        req.setAttribute(SAVE, API.PROJECT.SAVE);
        req.setAttribute("budgetTypes", BudgetSize.values());
        req.setAttribute("budgets", hibernator.query(Budget.class, null));
        showModal(req, resp);
    }
}
