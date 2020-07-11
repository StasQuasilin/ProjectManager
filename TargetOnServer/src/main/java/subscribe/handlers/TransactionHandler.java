package subscribe.handlers;

import entity.finance.Transaction;
import entity.user.User;
import org.json.simple.JSONArray;
import subscribe.Subscribe;
import utils.db.dao.daoService;
import utils.db.dao.finance.transactions.TransactionDAO;

/**
 * Created by DELL on 09.07.2020.
 */
public class TransactionHandler extends SubscribeHandler {

    private final TransactionDAO transactionDAO = daoService.getTransactionDAO();

    public TransactionHandler() {
        super(Subscribe.transactions);
    }

    @Override
    Object getItems(User user) {
        JSONArray array = new JSONArray();

        for (Transaction transaction : transactionDAO.getUserTransactions(user)){
            array.add(transaction.toJson());
        }

        return array;
    }
}
