package api.projects;

import api.socket.UpdateUtil;
import constants.API;
import controllers.ServletAPI;
import entity.project.Project;
import entity.task.Task;
import entity.user.User;
import org.json.simple.JSONObject;
import services.State;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 02.03.2020.
 */
@WebServlet(API.PROJECT_DELETE)
public class ProjectRemoveAPI extends ServletAPI{

    private final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            Project project = dao.getObjectById(Project.class, body.get(ID));
            dao.remove(project);
            removeTask(project.getTask(), getUser(req));
            write(resp, SUCCESS);
            updateUtil.onRemove(project);
        }
    }

    private void removeTask(Task task, User user) throws IOException {
        dao.remove(task);
        updateUtil.onRemove(task);
        for (Task t : dao.getTasksByParent(user, task, State.ignore)){
            removeTask(t, user);
        }
    }
}
