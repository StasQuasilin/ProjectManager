package api.projects;

import constants.API;
import entity.Project;
import entity.User;
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

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DATE = "date";
    private static final String DESCRIPTION = "description";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> body = PostUtil.parseBody(req);
        Project project;
        if (body.containsKey(ID)){
            project = hibernator.get(Project.class, "id", Integer.parseInt(body.get(ID)));
        }else {
            project = new Project();
            String uid = req.getSession().getAttribute("uid").toString();
            project.setCreator(hibernator.get(User.class, "id", Integer.parseInt(uid)));
        }
        project.setTitle(body.get(TITLE));

        if (body.containsKey(DATE)){
            project.setDate(DateParser.parseFromEditor(body.get(DATE)));
        }
        if (body.containsKey(DESCRIPTION)){
            project.setDescription(body.get(DESCRIPTION));
        }
        hibernator.save(project);
        body.clear();

    }
}
