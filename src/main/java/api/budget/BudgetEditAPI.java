package api.budget;

import api.socket.UpdateUtil;
import constants.API;
import controllers.ServletAPI;
import entity.budget.*;
import entity.user.User;
import org.json.simple.JSONObject;
import utils.BudgetCalculator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by szpt_user045 on 27.02.2020.
 */
@WebServlet(API.BUDGET_EDIT)
public class BudgetEditAPI extends ServletAPI {

    private final UpdateUtil updateUtil = new UpdateUtil();
    private final BudgetCalculator budgetCalculator = new BudgetCalculator();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            User user = getUser(req);
            Budget budget = dao.getObjectById(Budget.class, body.get(ID));
            if (budget == null){
                budget = new Budget();
                budget.setOwner(user);
                budget.setCreate(Timestamp.valueOf(LocalDateTime.now()));
                budget.setBudgetSize(BudgetSize.floated);
            }

            String title = String.valueOf(body.get(TITLE));
            budget.setTitle(title);

            BudgetType budgetType = BudgetType.valueOf(String.valueOf(body.get(TYPE)));
            budget.setBudgetType(budgetType);

            write(resp, SUCCESS);
            dao.save(budget);
            updateUtil.onSave(budget);

            float amount = Float.parseFloat(String.valueOf(body.get(AMOUNT)));
            if (budget.getBudgetSum() != amount){
                float d = amount - budget.getBudgetSum();
                Transaction transaction = new Transaction();
                transaction.setBudget(budget);
                transaction.setSum(d);
                transaction.setDate(Date.valueOf(LocalDate.now()));
                transaction.setType(d > 0 ? TransactionType.income : TransactionType.outcome);
                dao.save(transaction);
                budgetCalculator.calculate(user, budget, transaction);
            }

        }
    }
}
