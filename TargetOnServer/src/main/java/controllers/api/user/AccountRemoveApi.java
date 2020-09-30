package controllers.api.user;

import constants.ApiLinks;
import constants.Keys;
import constants.UrlLinks;
import controllers.api.API;
import entity.user.User;
import utils.answers.Answer;
import utils.answers.SuccessAnswer;
import utils.db.dao.access.DemoAccessDAO;
import utils.db.dao.category.CategoryDAO;
import utils.db.dao.daoService;
import utils.db.dao.finance.accounts.AccountDAO;
import utils.db.dao.finance.transactions.TransactionDAO;
import utils.db.dao.goal.GoalDAO;
import utils.db.dao.tree.TaskDAO;
import utils.db.dao.user.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(ApiLinks.ACCOUNT_REMOVE)
public class AccountRemoveApi extends API {

    private final GoalDAO goalDAO = daoService.getGoalDAO();
    private final TaskDAO taskDAO = daoService.getTaskDAO();
    private final UserDAO userDAO = daoService.getUserDAO();
    private final TransactionDAO transactionDAO = daoService.getTransactionDAO();
    private final AccountDAO accountDAO = daoService.getAccountDAO();
    private final CategoryDAO categoryDAO = daoService.getCategoryDAO();
    private final DemoAccessDAO demoAccessDAO = daoService.getDemoDAO();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final User user = getUser(req);

        transactionDAO.removeTransactions(user);
        accountDAO.removeAccounts(user);
        taskDAO.removeTasks(user);
        goalDAO.removeGoals(user);
        categoryDAO.removeCategories(user);
        demoAccessDAO.remove(user);
        userDAO.removeUser(user);
        final SuccessAnswer answer = new SuccessAnswer();
        answer.addAttribute(Keys.REDIRECT, req.getContextPath() + UrlLinks.LOGIN);
        write(resp, answer);
    }
}
