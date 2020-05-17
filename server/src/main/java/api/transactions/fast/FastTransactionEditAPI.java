package api.transactions.fast;

import api.socket.UpdateUtil;
import constants.API;
import controllers.ServletAPI;
import entity.transactions.Transaction;
import entity.transactions.fast.transaction.FastTransaction;
import entity.user.User;
import org.json.simple.JSONObject;
import utils.TransactionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(API.FAST_EDIT)
public class FastTransactionEditAPI extends ServletAPI {

    private final TransactionUtil transactionUtil = new TransactionUtil();
    private final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            User user = getUser(req);
            FastTransaction fast = dao.getObjectById(FastTransaction.class, body.get(ID));
            if (fast == null){
                fast = new FastTransaction();
                fast.setTransaction(new Transaction());
                fast.setOwner(user);
            }
            Transaction transaction = fast.getTransaction();
            transactionUtil.filTransaction(transaction, body, user);

            write(resp, SUCCESS);
            dao.save(transaction);
            dao.save(fast);
            updateUtil.onSave(fast);
        }
    }
}
