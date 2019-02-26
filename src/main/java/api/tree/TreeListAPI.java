package api.tree;

import constants.API;
import entity.Project;
import entity.Task;
import jdk.internal.org.objectweb.asm.tree.FieldInsnNode;
import org.json.simple.JSONArray;
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
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 26.02.2019.
 */
@WebServlet(API.TREE.LIST)
public class TreeListAPI extends HttpServlet {

    private static final Hibernator hibernator = Hibernator.getInstance();
    private static final String PROJECT = "project";
    private static final String TASK = "task";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> body = PostUtil.parseBody(req);
        Task task = null;
        if (body.containsKey(TASK)){
            task = hibernator.get(Task.class, "id", Integer.parseInt(body.get(TASK)));
        } else if (body.containsKey(PROJECT)){
            Project project = hibernator.get(Project.class, "id", Integer.parseInt(body.get(PROJECT)));
            task = project.getTask();
        }

        LinkedList<Task> sub = new LinkedList<>();
        if (task != null){
            sub.addAll(hibernator.query(Task.class, "parent", task));
        } else {
            sub.addAll(hibernator.query(Project.class, null).stream().map(Project::getTask).collect(Collectors.toList()));
        }
        PostUtil.write(resp, JsonParser.toJson(task, sub).toJSONString());


    }
}
