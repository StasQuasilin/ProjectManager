package api.projects;

import constants.API;
import controllers.IAPI;
import entity.*;
import entity.budget.Budget;
import entity.budget.BudgetSize;
import org.json.simple.JSONObject;
import utils.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by quasilin on 24.02.2019.
 */
@WebServlet(API.PROJECT.SAVE)
public class ProjectEditAPI extends IAPI {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            System.out.println(body);
            Project project;
            long id = -1;
            if (body.containsKey("id")) {
                id = (long) body.get("id");
            }
            boolean save = false;
            if (id != -1){
                project = hibernator.get(Project.class, "id", id);
            }else {
                project = new Project();
                String uid = req.getSession().getAttribute("uid").toString();
                project.setOwner(hibernator.get(User.class, "id", Integer.parseInt(uid)));
                Task task = new Task(project.getTitle());
                project.setTask(task);
                save = true;
            }

            String title = String.valueOf(body.get("title"));
            System.out.println(project.getTitle());
            if (Util.empty(project.getTitle()) || !project.getTitle().equals(title)){
                project.setTitle(title);
                save = true;
            }

            Date begin = Date.valueOf(LocalDate.parse((CharSequence) body.get("begin")));
            if (project.getBeginDate() == null || !project.getBeginDate().equals(begin)){
                project.setBeginDate(begin);
                save = true;
            }

            Date complete = Date.valueOf(LocalDate.parse((CharSequence) body.get("complete")));
            if (project.getCompleteDate() == null || !project.getCompleteDate().equals(complete)){
                project.setCompleteDate(complete);
                save = true;
            }

            JSONObject budgetJson = (JSONObject) body.get("budget");
            if (budgetJson != null){
                long budgetId = (long) budgetJson.get("id");
                if (budgetId != -1) {
                    Budget budget = project.getBudget();
                    if (budget == null || budgetId == -2) {
                        budget = new Budget();
                        budget.setTitle(project.getTitle());
                        budget.setOwner(project.getOwner());
                        project.setBudget(budget);
                        save = true;
                    }

                    float sum = Float.parseFloat(String.valueOf(budgetJson.get("sum")));
                    if (budget.getBudgetSum() != sum) {
                        budget.setBudgetSum(sum);
                        save = true;
                    }
                    BudgetSize type = BudgetSize.valueOf(String.valueOf(budgetJson.get("type")));
                    if (budget.getBudgetSize() != type) {
                        budget.setBudgetSize(type);
                        save = true;
                    }
                }
            }

            if (save){
                if (project.getBudget() != null) {
                    hibernator.save(project.getBudget());
                }
                hibernator.save(project.getTask(), project);
            }

            write(resp, SUCCESS);
        } else {
            write(resp, ERROR);
        }
    }
}
