package controllers.api.finance;

import constants.ApiLinks;
import controllers.api.API;
import entity.finance.Category;
import entity.finance.Transaction;
import entity.finance.TransactionType;
import utils.db.dao.daoService;
import utils.db.dao.finance.transactions.TransactionDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

import static constants.Keys.*;

@WebServlet(ApiLinks.TRANSACTION_SAVE)
public class EditTransactionAPI extends API {

    private final TransactionDAO transactionDAO = daoService.getTransactionDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

            float amount = body.getFloat(AMOUNT);
            transaction.setAmount(amount);

            String currency = body.getString(CURRENCY);
            transaction.setCurrency(currency);

            float rate = body.getFloat(RATE);
            transaction.setRate(rate);


            Category category = transaction.getCategory();
            if (category == null){
                category = new Category();
                category.setOwner(getUser(req));
                transaction.setCategory(category);
            }


            transactionDAO.saveTransaction(transaction);
            write(resp, SUCCESS_ANSWER);
        }
    }
}
