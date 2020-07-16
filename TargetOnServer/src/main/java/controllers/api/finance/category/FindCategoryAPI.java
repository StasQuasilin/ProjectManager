package controllers.api.finance.category;

import constants.ApiLinks;
import controllers.api.API;
import entity.finance.transactions.Category;
import entity.user.User;
import org.json.simple.JSONArray;
import utils.db.dao.category.CategoryDAO;
import utils.db.dao.daoService;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.KEY;

@WebServlet(ApiLinks.FIND_TRANSACTION_CATEGORY)
public class FindCategoryAPI extends API {

    private final CategoryDAO categoryDAO = daoService.getCategoryDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            final String key = body.getString(KEY);
            final User user = getUser(req);
            JSONArray array = new JSONArray();
            for (Category category : categoryDAO.findCategory(key, user)){
                array.add(category.shortJson());
            }
            write(resp, array.toJSONString());
        }
    }
}
