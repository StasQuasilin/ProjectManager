package controllers.api.goal;

import controllers.api.API;
import constants.ApiLinks;
import entity.Title;
import entity.finance.buy.BuyList;
import entity.finance.category.Header;
import entity.goal.Goal;
import entity.user.User;
import utils.answers.Answer;
import utils.answers.ErrorAnswer;
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
        Answer answer;
        if (body != null){
            System.out.println(body);
            Goal goal = goalDAO.getGoal(body.get(ID));
            if (goal == null){
                goal = new Goal();
            }

            User user = getUser(req);

            Title title = goal.getTitle();
            if (title == null){
                title = new Title();
                title.setOwner(user);
                goal.setTitle(title);
            }
            String titleString = body.getString(TITLE);
            if (titleString.isEmpty()){
                answer = new ErrorAnswer(EMPTY_TITLE);
            } else {
                title.setValue(titleString);

                final float budget = body.getFloat(BUDGET);
                goal.setBudget(budget);

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
                        buyList.setTitle(title);
                    } else {
//                    buyList.setTitle(buyListJson.getString(TITLE));
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
                answer = new SuccessAnswer();
                answer.addAttribute(ID, goal.getId());
            }
        } else {
            answer = new ErrorAnswer(EMPTY_BODY);
        }
        write(resp, answer);
    }
}
