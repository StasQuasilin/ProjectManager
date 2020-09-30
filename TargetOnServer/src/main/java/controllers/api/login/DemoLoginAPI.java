package controllers.api.login;

import constants.ApiLinks;
import constants.Keys;
import constants.UrlLinks;
import controllers.api.API;
import entity.finance.accounts.Account;
import entity.finance.accounts.AccountType;
import entity.finance.category.Category;
import entity.finance.transactions.Transaction;
import entity.finance.transactions.TransactionType;
import entity.goal.Goal;
import entity.task.Task;
import entity.task.TaskStatus;
import entity.user.DemoAccount;
import entity.user.User;
import entity.user.UserSettings;
import utils.LanguageBase;
import utils.answers.Answer;
import utils.answers.SuccessAnswer;
import utils.db.dao.access.DemoAccessDAO;
import utils.db.dao.daoService;
import utils.db.dao.finance.accounts.AccountDAO;
import utils.db.dao.finance.currency.CurrencyDAO;
import utils.db.dao.finance.transactions.TransactionDAO;
import utils.db.dao.goal.GoalDAO;
import utils.db.dao.user.UserDAO;
import utils.finances.AccountUtil;
import utils.finances.TransactionUtil;
import utils.json.JsonObject;
import utils.savers.TransactionSaver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import static constants.Keys.*;

@WebServlet(ApiLinks.DEMO)
public class DemoLoginAPI extends API {

    private final UserDAO userDAO = daoService.getUserDAO();
    private final DemoAccessDAO demoAccessDAO = daoService.getDemoDAO();

    LanguageBase base = LanguageBase.getBase();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String locale = LanguageBase.DEFAULT_LANGUAGE;
        final JsonObject body = parseBody(req);
        if (body != null){
            if (body.containKey(Keys.LOCALE)){
                final String l = body.getString(Keys.LOCALE);
                if (l != null && !l.isEmpty()){
                    locale = l;
                }
            }
        }

        final String ip = req.getRemoteAddr();
        DemoAccount demoAccount = demoAccessDAO.getAccountByIp(ip);
        if (demoAccount == null){
            demoAccount = new DemoAccount();
            demoAccount.setIp(ip);
            demoAccount.setCreate(Date.valueOf(LocalDate.now()));
            User user = new User();
            user.setSurname("Demo");
            UserSettings settings = new UserSettings();
            settings.setLocale(locale);
            settings.setUser(user);
            userDAO.save(user);
            userDAO.saveSettings(settings);
            initDemoData(user, locale);
            demoAccount.setUser(user);
            demoAccessDAO.save(demoAccount);
        }
        final User user = demoAccount.getUser();
        final HttpSession session = req.getSession();
        session.setAttribute(USER, user);
        session.setAttribute(LOCALE, locale);
        session.setAttribute(DEMO, demoAccount.getCreate());

        Answer answer = new SuccessAnswer();
        answer.addAttribute(REDIRECT, UrlLinks.HOME);
        answer.addAttribute(TOKEN, user.getId());

        write(resp, answer);
    }

    private void initDemoData(User user, String locale) {
        initGoals(user, locale);
        initAccounts(user, locale);
    }
    final CurrencyDAO currencyDAO = daoService.getCurrencyDAO();
    int transactionNumber = 0;
    private void initAccounts(User user, String locale) {
        final AccountDAO accountDAO = daoService.getAccountDAO();

        final AccountUtil accountUtil = new AccountUtil();
        final AccountType[] accountTypes = AccountType.values();

        for (int i = 0; i < 3; i++){
            Account account = new Account();
            account.setTitle(base.get(locale, "demo.account") + Keys.SPACE + Keys.SHARP + (i + 1));
            account.setShow(true);
            account.setOwner(user);
            AccountType type = accountTypes[i];
            if (type == AccountType.card){
                account.setLimit(5000);
            }
            account.setType(type);
            account.setCurrency(currencyDAO.getUserCurrency(user).get(0));
            accountDAO.saveAccount(account);
            accountUtil.updateSum(account, i * 1200, false);
            initTransactions(account, ++transactionNumber, user, locale);
        }
    }
    private final TransactionSaver transactionSaver = new TransactionSaver();
    private void initTransactions(Account account, int transactionNumber, User user, String locale) {
        final LocalDate now = LocalDate.now();
        for (int i = 0; i < 10; i++){
            Transaction transaction = new Transaction();
            transaction.setDate(Date.valueOf(now.minusDays(i)));
            Category category = new Category();
            category.setOwner(user);
            category.setTitle(base.get(locale, "demo.transaction") + SPACE + SHARP + transactionNumber);
            transaction.setAmount(200);
            transaction.setTransactionType(TransactionType.spending);
            transaction.setCategory(category);
            transaction.setCurrency(currencyDAO.getUserCurrency(user).get(0));
            transaction.setAccountFrom(account);
            transaction.setRate(1);
            transactionSaver.save(transaction);
        }
    }

    private void initGoals(User user, String locale) {
        GoalDAO goalDAO = daoService.getGoalDAO();
        final LocalDate now = LocalDate.now();
        final LocalDate plus3 = now.plusDays(3);

        Date begin = Date.valueOf(now);
        Date end = Date.valueOf(plus3);

        for (int i = 0 ; i < 3; i++){
            Goal goal = new Goal();

            goal.setBegin(begin);
            goal.setEnd(end);

            goal.setBudget((i + 1) * 1000);

            Category category = new Category();
            category.setTitle(base.get(locale, "demo.goal") + Keys.SPACE + Keys.SHARP + (i + 1));
            category.setOwner(user);
            goal.setCategory(category);

            goalDAO.saveGoal(goal);
            initTasks(goal.getCategory(), 3, locale, user);
        }
    }

    private void initTasks(Category parent, int deep, String locale, User user) {
        for (int i = 0; i < deep; i++){
            for (int j = 0; j < 3; j++){
                Task task = new Task();
                Category category = new Category();
                category.setTitle(base.get(locale, "demo.task") + Keys.SPACE + Keys.SHARP + (j + 1));
                category.setOwner(user);
                category.setParent(parent);
                task.setCategory(category);

                task.setStatus(TaskStatus.active);
                initTasks(category, deep - 1, locale, user);
            }
        }
    }
}
