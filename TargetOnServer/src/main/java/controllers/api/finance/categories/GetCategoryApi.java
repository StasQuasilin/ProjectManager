package controllers.api.finance.categories;

import constants.ApiLinks;
import constants.Keys;
import controllers.api.API;
import entity.finance.category.Category;
import entity.user.User;
import org.json.simple.JSONArray;
import utils.answers.Answer;
import utils.answers.SuccessAnswer;
import utils.db.dao.category.CategoryDAO;
import utils.db.dao.daoService;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;

@WebServlet(ApiLinks.GET_CATEGORIES)
public class GetCategoryApi extends API {

    private final CategoryDAO categoryDAO = daoService.getCategoryDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final User user = getUser(req);
        Category parent = null;
        final JsonObject body = parseBody(req);
        if (body != null){
            if (body.containKey(Keys.PARENT)){
                parent = categoryDAO.getCategory(body.get(Keys.PARENT));
            }
        }
        final JSONArray array = new JSONArray();
        for (Category category : categoryDAO.getCategories(user, parent)){
            array.add(category.toJson());
        }
        Answer answer = new SuccessAnswer();
        answer.addAttribute(Keys.RESULT, array);
        write(resp, answer);
    }
}
