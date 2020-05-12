package api.socket.handlers;

import api.socket.Subscribe;
import api.socket.handlers.ISocketHandler;
import entity.budget.Budget;
import entity.user.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.websocket.Session;
import java.io.IOException;

/**
 * Created by szpt_user045 on 26.02.2020.
 */
public class BudjetHandlet extends ISocketHandler {
    public BudjetHandlet(Subscribe subscribe) {
        super(subscribe);
    }

    @Override
    public void onSubscribe(User user, Session session) throws IOException {
        JSONArray array = pool.getArray();

        for (Budget budget : dao.getBudgetsByUser(user)){
            array.add(budget.toJson());
        }
        JSONObject object = pool.getObject();
        object.put(UPDATE, array);
        send(session, object);
    }
}
