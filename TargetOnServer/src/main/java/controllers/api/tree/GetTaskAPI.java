package controllers.api.tree;

import constants.ApiLinks;
import controllers.api.API;
import entity.finance.category.Category;
import entity.finance.category.Header;
import org.json.simple.JSONObject;
import utils.db.dao.category.CategoryDAO;
import utils.db.dao.daoService;
import utils.db.dao.tree.TaskDAO;
import utils.json.JsonObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.*;

@WebServlet(ApiLinks.GET_TASK)
public class GetTaskAPI extends API {

    private final TaskDAO taskDAO = daoService.getTaskDAO();
    private final CategoryDAO categoryDAO = daoService.getCategoryDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JsonObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            final Header category = categoryDAO.getCategory(body.get(PARENT));

            JSONObject jsonObject = new JSONObject();
            if (category != null){
//                jsonObject.put(ITEM, category.toJson());
//                jsonObject.put(ROOT, getRoot(category).getId());
//                final List<Task> children = taskDAO.getTasksByParent(category);
//                JSONArray array = new JSONArray();
//                for (Task task : children){
//                    System.out.println(task.getTitle());
//                    array.add(task.toJson());
//                }
//                jsonObject.put(CHILDREN, array);
            }
            write(resp, jsonObject);
        }
    }

    private Category getRoot(Category category) {
//        while (category.getParent() != null){
//            category = category.getParent();
//        }
        return category;
    }
}
