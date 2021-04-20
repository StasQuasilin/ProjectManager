package controllers.api.calendar;

import constants.ApiLinks;
import controllers.api.API;
import entity.goal.Goal;
import entity.task.Task;
import org.json.simple.JSONArray;
import utils.answers.Answer;
import utils.answers.SuccessAnswer;
import utils.db.dao.daoService;
import utils.db.dao.goal.GoalDAO;
import utils.json.JsonObject;
import utils.tree.CalendarTaskUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import static constants.Keys.*;

@WebServlet(ApiLinks.RANDOM_TASK)
public class RandomTaskGeneratorAPI extends API {

    private final CalendarTaskUtil calendarTaskUtil = new CalendarTaskUtil();
    private final GoalDAO goalDAO = daoService.getGoalDAO();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if(body != null){
            final Goal goal = goalDAO.getGoal(body.get(GOAL));
            final int count = body.getInt(COUNT);
            LinkedList<Task> linkedList = calendarTaskUtil.getActiveParentItems(goal.getTitle().getId());
            JSONArray result = new JSONArray();
            Random random = new Random();
            for (int i = 0; i < count; i++){
                final int idx = random.nextInt(linkedList.size());
                result.add(linkedList.remove(idx).toJson());
            }
            Answer answer = new SuccessAnswer();
            answer.addAttribute(RESULT, result);
            write(resp, answer);
        }
    }
}
