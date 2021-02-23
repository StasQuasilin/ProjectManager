package controllers.api.goal;

import controllers.api.API;
import constants.ApiLinks;
import entity.finance.buy.BuyList;
import entity.finance.category.Category;
import entity.finance.category.Header;
import entity.goal.Goal;
import entity.user.User;
import utils.answers.Answer;
import utils.answers.SuccessAnswer;
import utils.db.dao.daoService;
import utils.db.dao.finance.buy.BuyListDAO;
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
    private final BuyListDAO buyListDAO = daoService.getBuyListDAO();

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

            final float budget = body.getFloat(BUDGET);

            Header header = goal.getHeader();
            if (header == null){
                header = new Header();
                header.setOwner(user);
                goal.setHeader(header);
            }

            goal.setBudget(budget);

            String title = body.getString(TITLE);
            header.setTitle(title);

            if (body.containKey(BEGIN)){
                Date dateBegin = Date.valueOf(body.getString(BEGIN));
                goal.setBegin(dateBegin);
                if (body.containKey(END)){
                    Date dateEnd = Date.valueOf(body.getString(END));
                    goal.setEnd(dateEnd);
                } else {
                    goal.setEnd(null);
                }
            } else {
                goal.setBegin(null);
            }

            if (body.containKey(BUY_LIST)){
                final JsonObject buyListJson = new JsonObject(body.get(BUY_LIST));
                BuyList buyList = null;
                if (buyListJson.containKey(ID)){
                    buyList = buyListDAO.getList(buyListJson.get(ID));
                }
                if (buyList == null){
                    buyList = new BuyList();
                    buyList.setOwner(user);
                }

                if (buyListJson.getBoolean(SEPARATED)){
                    buyList.setTitle(header.getTitle());
                } else {
                    buyList.setTitle(buyListJson.getString(TITLE));
                }
                buyListDAO.saveList(buyList);
                goal.setBuyList(buyList.getId());
            } else {
                final BuyList list = buyListDAO.getList(goal.getBuyList());
                if (list != null){
                    buyListDAO.removeList(list);
                }
                goal.setBuyList(-1);
            }

            goalDAO.saveGoal(goal);
            Answer answer = new SuccessAnswer();
            answer.addAttribute(ID, goal.getId());
            write(resp, answer);



        }
    }


}
