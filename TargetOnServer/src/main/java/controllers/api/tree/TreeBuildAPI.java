package controllers.api.tree;

import constants.ApiLinks;
import controllers.api.API;
import entity.finance.category.Category;
import entity.task.Task;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.db.dao.category.CategoryDAO;
import utils.db.dao.daoService;
import utils.db.dao.tree.TaskDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static constants.Keys.CHILDREN;
import static constants.Keys.ID;
@WebServlet(ApiLinks.TREE_BUILDER)
public class TreeBuildAPI extends API {

    private final CategoryDAO categoryDAO = daoService.getCategoryDAO();
    private final TaskDAO taskDAO = daoService.getTaskDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            Category root = categoryDAO.getCategory(body.get(ID));
            write(resp, buildTree(taskDAO.getTaskByCategory(root), root));
        }
    }

    private JSONObject buildTree(Task parent, Category category) {

        JSONObject jsonObject = parent != null ? parent.shortJson() : category.shortJson();

        final List<Task> children = taskDAO.getTasksByParent(category);

        JSONArray array = new JSONArray();
        for (Task task : children) {
//            array.add(buildTree(task, task.getHeader()));
        }
        jsonObject.put(CHILDREN, array);
        return jsonObject;
    }
}
