package api.tree;

import constants.API;
import entity.Project;
import entity.Task;
import services.hibernate.Hibernator;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 26.02.2019.
 */
@WebServlet(API.Tree.EDIT_TASK)
public class EditTaskAPI extends HttpServlet {

    private static final Hibernator HIBERNATOR = Hibernator.getInstance();
    private static final String ID = "id";
    private static final String NAME = "name";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> body = PostUtil.parseBody(req);
        Task task = HIBERNATOR.get(Task.class, "id", Integer.parseInt(body.get(ID)));
        task.setTitle(body.get(NAME));
        HIBERNATOR.save(task);
        Project project =HIBERNATOR.get(Project.class, "task", task);
        if (project != null){
            project.setTitle(task.getTitle());
            HIBERNATOR.save(project);
        }
        body.clear();
    }
}
