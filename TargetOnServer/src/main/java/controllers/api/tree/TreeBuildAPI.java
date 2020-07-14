package controllers.api.tree;

import constants.ApiLinks;
import controllers.api.API;
import entity.finance.Category;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.db.dao.category.CategoryDAO;
import utils.db.dao.daoService;
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject body = parseBody(req);
        if (body != null){
            Category root = categoryDAO.getCategory(body.get(ID));
            write(resp, buildTree(root));
        }
    }

    private JSONObject buildTree(Category parent) {
        JSONObject jsonObject = parent.shortJson();
        List<Category> children = categoryDAO.getChildren(parent);
        JSONArray array = new JSONArray();
        for (Category category : children){
            array.add(buildTree(category));
        }
        jsonObject.put(CHILDREN, array);
        return jsonObject;

    }
}
