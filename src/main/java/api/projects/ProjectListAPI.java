package api.projects;

import constants.API;
import entity.Project;
import entity.Task;
import entity.TaskStatus;
import javafx.beans.binding.ObjectExpression;
import org.json.simple.JSONArray;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import services.hibernate.Hibernator;
import utils.JsonParser;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Created by quasilin on 24.02.2019.
 */
@WebServlet(API.PROJECT.LIST)
public class ProjectListAPI extends HttpServlet {

    private static final Hibernator hibernator = Hibernator.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uid = req.getSession().getAttribute("uid").toString();
        List<Project> projects = hibernator.query(Project.class, "owner", Integer.parseInt(uid));

        JSONArray array = new JSONArray();
        for (Project p : projects){
            Task task = p.getTask();
            if (task == null){
                task = new Task();
                p.setTask(task);
                hibernator.save(task, p);
            }
            int active = calculateTaskCount(task, TaskStatus.active);
            int done = calculateTaskCount(task, TaskStatus.done);
            int canceled = calculateTaskCount(task, TaskStatus.canceled);

            array.add(JsonParser.toJson(p, active, done, canceled));
        }

        PostUtil.write(resp, array.toJSONString());
        array.clear();
    }
    synchronized int calculateTaskCount(Task parent, TaskStatus status){
        List<Task> tasks = getChildTask(parent, status);
        int total = tasks.size();
        for (Task t : tasks){
            total += calculateTaskCount(t, status);
        }
        tasks.clear();
        return total;

    }
    HashMap<String, Object> parameters;
    synchronized List<Task> getChildTask(Task parent, TaskStatus status){
        parameters = new HashMap<>();
        parameters.put("parent", parent);
        parameters.put("status", status.toString());
        return hibernator.query(Task.class, parameters);
    }
}
