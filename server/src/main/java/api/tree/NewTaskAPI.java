package api.tree;

import constants.API;
import entity.task.Task;
import services.LanguageBase;
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
@WebServlet(API.Tree.NEW_TASK)
public class NewTaskAPI extends HttpServlet {

    private static final Hibernator hibernator = Hibernator.getInstance();
    private static final LanguageBase LANGUAGE_BASE = LanguageBase.getBase();
    private static final String PARENT = "parent";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> body = PostUtil.parseBody(req);
        if (body.containsKey(PARENT)){
            String language = req.getSession().getAttribute("language").toString();
            Task parent = hibernator.get(Task.class, "id", Integer.parseInt(body.get(PARENT)));
            Task task = new Task(LANGUAGE_BASE.get(language, "new.task"));
            task.setParent(parent);
            hibernator.save(task);
        }
        body.clear();
    }
}
