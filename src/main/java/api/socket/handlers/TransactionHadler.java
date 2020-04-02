package api.socket.handlers;

import api.socket.Subscribe;
import entity.transactions.Transaction;
import entity.user.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.websocket.Session;
import java.io.IOException;

/**
 * Created by szpt_user045 on 26.02.2020.
 */
public class TransactionHadler extends ISocketHandler {
    public TransactionHadler(Subscribe subscribe) {
        super(subscribe);
    }

    @Override
    public void onSubscribe(User user, Session session) throws IOException {
        JSONArray array = pool.getArray();

        for (Transaction transaction : dao.getTransactionsByUser(user, null)){
            array.add(transaction.toJson());
        }

        JSONObject object = pool.getObject();
        object.put(UPDATE, array);
        send(session, object);
    }
}
