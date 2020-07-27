package controllers.api.goal;

import controllers.api.API;
import constants.ApiLinks;
import entity.finance.category.Category;
import entity.goal.Goal;
import entity.user.User;
import utils.db.dao.daoService;
import utils.db.dao.goal.GoalDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

import static constants.Keys.*;

@WebServlet(ApiLinks.GOAL_SAVE)
public class EditGoalAPI extends API {

    private final GoalDAO goalDAO = daoService.getGoalDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            Goal goal = goalDAO.getGoal(body.get(ID));
            if (goal == null){
                goal = new Goal();
            }

            User user = getUser(req);
            System.out.println(user);

            final float budget = body.getFloat(BUDGET);
            final String currency = body.getString(CURRENCY);

            Category category = goal.getCategory();
            if (category == null){
                category = new Category();
                category.setOwner(user);
                goal.setCategory(category);
            }

            goal.setBudget(budget);
            category.setCurrency(currency);

            String title = body.getString(TITLE);
            category.setTitle(title);

            if (body.containKey(DATE_BEGIN)){
                Date dateBegin = Date.valueOf(body.getString(DATE_BEGIN));
                goal.setBegin(dateBegin);
            }
            if (body.containKey(DATE_END)){
                Date dateEnd = Date.valueOf(body.getString(DATE_END));
                goal.setEnd(dateEnd);
            }

            goalDAO.saveGoal(goal);
            write(resp, SUCCESS_ANSWER);

        }
    }


}
