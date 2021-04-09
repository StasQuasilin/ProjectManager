package controllers.api.tree;

import constants.ApiLinks;
import controllers.api.API;
import entity.finance.category.Category;
import entity.finance.category.Header;
import entity.task.Task;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.db.dao.TitleDAO;
import utils.db.dao.category.CategoryDAO;
import utils.db.dao.daoService;
import utils.db.dao.tree.TaskDAO;
import utils.json.JsonObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static constants.Keys.*;

@WebServlet(ApiLinks.GET_TASK)
public class GetTaskAPI extends API {

    private final TaskDAO taskDAO = daoService.getTaskDAO();
    private final TitleDAO titleDAO = daoService.getTitleDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JsonObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            final Header header = titleDAO.getHeader(body.get(PARENT));

            JSONObject jsonObject = new JSONObject();
            if (header != null){
                jsonObject.put(ITEM, header.toJson());
                jsonObject.put(ROOT, getRoot(header).getId());
                final List<Task> children = taskDAO.getTasksByParent(header);
                JSONArray array = new JSONArray();
                for (Task task : children){
                    array.add(task.toJson());
                }
                jsonObject.put(CHILDREN, array);
            }
            write(resp, jsonObject);
        }
    }

    private Header getRoot(Header category) {
        while (category.getParent() != null){
            category = category.getParent();
        }
        return category;
    }
}
