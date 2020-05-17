package api.transactions.fast;

import api.socket.UpdateUtil;
import constants.API;
import controllers.ServletAPI;
import entity.transactions.Transaction;
import entity.transactions.fast.transaction.FastTransaction;
import org.json.simple.JSONObject;
import utils.BudgetCalculator;
import utils.TransactionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@WebServlet(API.INSERT_BY_FAST)
public class InsertFastTransactionAPI extends ServletAPI {

    private final UpdateUtil updateUtil = new UpdateUtil();
    private final TransactionUtil transactionUtil = new TransactionUtil();
    private final BudgetCalculator budgetCalculator = new BudgetCalculator();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            FastTransaction fast = dao.getObjectById(FastTransaction.class, body.get(ID));
            if(fast != null){
                Transaction transaction = transactionUtil.copy(fast.getTransaction());;
                transaction.setDate(Date.valueOf(LocalDate.now()));
                transaction.setOwner(fast.getOwner());
                write(resp, SUCCESS);
                dao.save(transaction);
                updateUtil.onSave(transaction);
                budgetCalculator.calculatePointRoot(transaction);

                fast.increment();
                dao.save(fast);
                updateUtil.onSave(fast);

            }
        }
    }
}
