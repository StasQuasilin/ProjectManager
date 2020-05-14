package api.socket.handlers;

import api.socket.Subscribe;
import entity.budget.Account;
import entity.transactions.buy.list.BuyList;
import entity.user.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.websocket.Session;
import java.io.IOException;

public class BuyListHandler extends ISocketHandler {
    public BuyListHandler(Subscribe subscribe) {
        super(subscribe);
    }

    @Override
    public void onSubscribe(User user, Session session) throws IOException {
        JSONArray array = pool.getArray();

        for (BuyList buyList : dao.getBuyListByUser(user)){
            array.add(buyList.toJson());
        }
        JSONObject object = pool.getObject();
        object.put(UPDATE, array);
        send(session, object);
    }
}
