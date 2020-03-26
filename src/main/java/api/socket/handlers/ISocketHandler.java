package api.socket.handlers;

import api.socket.Subscribe;
import constants.Keys;
import entity.user.User;
import org.json.simple.JSONObject;
import services.hibernate.dbDAO;
import services.hibernate.dbDAOService;
import utils.JsonPool;

import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 17.02.2020.
 */
public abstract class ISocketHandler implements Keys {

    private final HashMap<User, Session> sessions = new HashMap<>();
    public JsonPool pool = JsonPool.getPool();
    public dbDAO dao = dbDAOService.getDao();

    private Subscribe subscribe;
    public ISocketHandler(Subscribe subscribe) {
        this.subscribe = subscribe;
    }

    public void send(User user, JSONObject data) throws IOException {
        if (sessions.containsKey(user)) {
            send(sessions.get(user), data);
        }
    }

    public void send(Session session, JSONObject data) throws IOException {
        JSONObject object = pool.getObject();

        object.put(TYPE, subscribe.toString());
        object.put(DATA, data);
        session.getBasicRemote().sendText(object.toJSONString());
        pool.put(object);
    }

    public synchronized void subscribe(User user, Session session) throws IOException {
        sessions.put(user, session);
        onSubscribe(user, session);
    }

    public synchronized void unsubscribe(User user){
        sessions.remove(user);
    }

    public abstract void onSubscribe(User user, Session session) throws IOException;

    public Subscribe getSubscribe() {
        return subscribe;
    }

//    JSONArray array = pool.getArray();
//
//        for (Transaction transaction : dao.getTransactionsByUser(user, null)){
//            array.add(transaction.toJson());
//        }
//
//        JSONObject object = pool.getObject();
//        object.put(UPDATE, array);
//        send(session, object);
}
