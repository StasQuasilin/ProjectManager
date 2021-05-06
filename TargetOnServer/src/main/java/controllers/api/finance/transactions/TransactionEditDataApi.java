package controllers.api.finance.transactions;

import constants.ApiLinks;
import controllers.api.API;
import entity.finance.accounts.Account;
import entity.finance.category.Header;
import entity.finance.transactions.Transaction;
import entity.finance.transactions.TransactionDetail;
import entity.finance.transactions.TransactionType;
import entity.user.User;
import org.json.simple.JSONArray;
import utils.answers.Answer;
import utils.answers.SuccessAnswer;
import utils.db.dao.category.CategoryDAO;
import utils.db.dao.daoService;
import utils.db.dao.finance.accounts.AccountDAO;
import utils.db.dao.finance.currency.CurrencyDAO;
import utils.db.dao.finance.transactions.TransactionDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static constants.Keys.*;
import static constants.Keys.CATEGORY;

@WebServlet(ApiLinks.TRANSACTION_SAVE + ApiLinks.DATA)
public class TransactionEditDataApi extends API {

    private final AccountDAO accountDAO = daoService.getAccountDAO();
    private final CurrencyDAO currencyDAO = daoService.getCurrencyDAO();
    private final TransactionDAO transactionDAO = daoService.getTransactionDAO();
    private final CategoryDAO categoryDAO = daoService.getCategoryDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if(body != null){
            Answer answer = new SuccessAnswer();
            if (body.containKey(ID)) {
                Transaction transaction = transactionDAO.getTransaction(body.get(ID));
                if (transaction != null){
                    answer.addAttribute(TRANSACTION, transaction.toJson());
                    req.setAttribute(TRANSACTION, transaction);
                    JSONArray detailArray = new JSONArray();
                    for (TransactionDetail detail : transactionDAO.getDetails(transaction.getId())){
                        detailArray.add(detail.toJson());
                    }
                    answer.addAttribute(DETAILS, detailArray);
                }
            } else {
                if (body.containKey(CATEGORY)){
                    final Header header = categoryDAO.getCategory(body.get(CATEGORY));
                    if (header != null){
                        answer.addAttribute(HEADER, header.toJson());
                    }
                }
            }
            final User user = getUser(req);
            final JSONArray accountArray = new JSONArray();
            for (Account account : accountDAO.getUserAccounts(user, false)){
                accountArray.add(account.toJson());
            }
            answer.addAttribute(ACCOUNTS, accountArray);
            final JSONArray currencyArray = new JSONArray();
            for (String currency : currencyDAO.getUserCurrency(user)){
                currencyArray.add(currency);
            }
            answer.addAttribute(CURRENCY, currencyArray);
            final JSONArray typesArray = new JSONArray();
            for (TransactionType type : TransactionType.values()){
                typesArray.add(type);
            }
            answer.addAttribute(TYPES, typesArray);
            write(resp, answer);
        }
    }
}
