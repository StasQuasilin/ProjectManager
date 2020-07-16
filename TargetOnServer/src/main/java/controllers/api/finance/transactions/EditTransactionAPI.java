package controllers.api.finance.transactions;

import constants.ApiLinks;
import controllers.api.API;
import entity.finance.accounts.Account;
import entity.finance.transactions.Category;
import entity.finance.transactions.Transaction;
import entity.finance.transactions.TransactionType;
import utils.CategoryUtil;
import utils.db.dao.daoService;
import utils.db.dao.finance.accounts.AccountDAO;
import utils.db.dao.finance.transactions.TransactionDAO;
import utils.finances.TransactionUtil;
import utils.json.JsonObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

import static constants.Keys.*;

@WebServlet(ApiLinks.TRANSACTION_SAVE)
public class EditTransactionAPI extends API {

    private final AccountDAO accountDAO = daoService.getAccountDAO();
    private final TransactionDAO transactionDAO = daoService.getTransactionDAO();
    private final CategoryUtil categoryUtil = new CategoryUtil();
    private final TransactionUtil transactionUtil = new TransactionUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JsonObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            Transaction transaction = transactionDAO.getTransaction(body.get(ID));
            if (transaction == null){
                transaction = new Transaction();
            }

            TransactionType type = TransactionType.valueOf(body.getString(TYPE));
            transaction.setTransactionType(type);

            Date date = Date.valueOf(body.getString(DATE));
            transaction.setDate(date);

            Account prevAccountFrom = null;
            if (body.containKey(ACCOUNT_FROM)){
                final Account account = accountDAO.getAccount(body.get(ACCOUNT_FROM));
                prevAccountFrom = transaction.getAccountFrom();
                transaction.setAccountFrom(account);
            }
            Account prevAccountTo = null;
            if (body.containKey(ACCOUNT_TO)){
                final Account account = accountDAO.getAccount(body.get(ACCOUNT_TO));
                prevAccountTo = transaction.getAccountTo();
                transaction.setAccountTo(account);
            }

            float amount = body.getFloat(AMOUNT);
            if (type == TransactionType.spending || type == TransactionType.transfer){
                amount *= -1;
            }
            transaction.setAmount(amount);

            String currency = body.getString(CURRENCY);
            transaction.setCurrency(currency);

            float rate = body.getFloat(RATE);
            transaction.setRate(rate);

            Category category = transaction.getCategory();
            if (category == null){
                category = categoryUtil.getCategory(new JsonObject(body.get(CATEGORY)), getUser(req));
                transaction.setCategory(category);
            }

            transactionDAO.saveTransaction(transaction);
            write(resp, SUCCESS_ANSWER);
            if (prevAccountFrom != null && prevAccountFrom.equals(transaction.getAccountFrom())){
                transactionUtil.removePoint(transaction, prevAccountFrom);
            }
            if (prevAccountTo != null && prevAccountTo.equals(transaction.getAccountTo())){
                transactionUtil.removePoint(transaction, prevAccountTo);
            }
        }
    }
}
