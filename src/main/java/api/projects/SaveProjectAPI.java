package api.projects;

import constants.API;
import entity.*;
import services.LanguageBase;
import services.hibernate.Hibernator;
import utils.DateParser;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by quasilin on 24.02.2019.
 */
@WebServlet(API.PROJECT.SAVE)
public class SaveProjectAPI extends HttpServlet {

    private static final Hibernator hibernator = Hibernator.getInstance();
    private static final LanguageBase LANGUAGE_BASE = LanguageBase.getBase();

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String BEGIN_DATE = "begin_date";
    private static final String COMPLETE_DATE = "complete_date";
    private static final String DESCRIPTION = "description";
    private static final String BUDGET_TYPE = "budgetType";
    private static final String BUDGET_SUM = "budgetSum";
    private static final String BUDGET_CURRENCY = "budget_currency";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> body = PostUtil.parseBody(req);
        Project project;
        if (body.containsKey(ID)){
            project = hibernator.get(Project.class, "id", Integer.parseInt(body.get(ID)));
        }else {
            project = new Project();
            String uid = req.getSession().getAttribute("uid").toString();
            project.setOwner(hibernator.get(User.class, "id", Integer.parseInt(uid)));
            Task task = new Task(project.getTitle());
            project.setTask(task);
        }

        project.setTitle(body.get(TITLE));
        project.setBeginDate(DateParser.parseFromEditor(body.get(BEGIN_DATE)));
        project.setCompleteDate(DateParser.parseFromEditor(body.get(COMPLETE_DATE)));

        if (body.containsKey(DESCRIPTION)){
            project.setDescription(body.get(DESCRIPTION));
        }

        hibernator.save(project.getTask(), project);
        body.clear();
        PostUtil.write(resp, ":)");

    }
}
