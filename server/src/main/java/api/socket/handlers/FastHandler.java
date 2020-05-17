package api.socket.handlers;

import api.socket.Subscribe;
import entity.transactions.buy.list.BuyList;
import entity.transactions.fast.transaction.FastTransaction;
import entity.user.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.websocket.Session;
import java.io.IOException;

public class FastHandler extends ISocketHandler{

    public FastHandler(Subscribe subscribe) {
        super(subscribe);
    }

    @Override
    public void onSubscribe(User user, Session session) throws IOException {
        JSONArray array = pool.getArray();

        for (FastTransaction fastTransaction : dao.getFastTransactionsByUser(user)){
            array.add(fastTransaction.toJson());
        }
        JSONObject object = pool.getObject();
        object.put(UPDATE, array);
        send(session, object);
    }
}
