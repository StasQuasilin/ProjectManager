package api.projects;

import api.socket.UpdateUtil;
import constants.API;
import controllers.ServletAPI;
import entity.budget.Budget;
import entity.budget.BudgetSize;
import entity.budget.BudgetType;
import entity.project.Project;
import entity.project.ProjectType;
import entity.project.Task;
import entity.project.TaskStatus;
import entity.user.User;
import org.json.simple.JSONObject;
import utils.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by quasilin on 24.02.2019.
 */
@WebServlet(API.PROJECT.SAVE)
public class ProjectEditAPI extends ServletAPI {

    private UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            System.out.println(body);
            User user = getUser(req);
            Project project = dao.getObjectById(Project.class, body.get(ID));

            if (project == null){
                project = new Project();
                project.setOwner(user);
                Task task = new Task();
                task.setStatus(TaskStatus.active);
                project.setTask(task);
                ProjectType type = ProjectType.valueOf(String.valueOf(body.get(TYPE)));
                project.setType(type);
            }

            String title = String.valueOf(body.get(TITLE));
            project.getTask().setTitle(title);

            Date begin = Date.valueOf(String.valueOf(body.get(BEGIN)));
            if (project.getBeginDate() == null || !project.getBeginDate().equals(begin)){
                project.setBeginDate(begin);
            }

            Date complete = Date.valueOf(String.valueOf(body.get(END)));
            if (project.getCompleteDate() == null || !project.getCompleteDate().equals(complete)){
                project.setCompleteDate(complete);
            }
            dao.save(project.getTask());
            dao.save(project);

            write(resp, SUCCESS);
            updateUtil.onSave(project);
        }
    }
}
