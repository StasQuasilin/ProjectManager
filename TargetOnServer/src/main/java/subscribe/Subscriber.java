package subscribe;

import entity.user.User;
import org.json.simple.JSONObject;
import subscribe.handlers.AccountHandler;
import subscribe.handlers.GoalHandler;
import subscribe.handlers.SubscribeHandler;
import subscribe.handlers.TransactionHandler;
import utils.UpdateAction;
import utils.json.JsonAble;

import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static constants.Keys.DATA;
import static constants.Keys.SUBSCRIBE;

public final class Subscriber {

    private static final Subscriber instance = new Subscriber();
    private static int count = 0;

    private Subscriber() {
        count++;
        System.out.println("Create subscriber #" + count);
    }
    private final HashMap<Subscribe, SubscribeHandler> handlers = new HashMap<>();
    {
        addHandler(new GoalHandler());
        addHandler(new TransactionHandler());
        addHandler(new AccountHandler());
    }

    private void addHandler(SubscribeHandler handler) {
        handlers.put(handler.getSubscribe(), handler);
    }

    public static Subscriber getInstance() {
        return instance;
    }

    final HashMap<User, ArrayList<Subscribe>> subscribes = new HashMap<>();
    final HashMap<User, ArrayList<Session>> sessionMap = new HashMap<>();

    public void subscribe(User user, Subscribe subscribe, Session session){
        System.out.println("User '" + user.toString() + " subscribe on " + subscribe.toString());
        SubscribeHandler handler = handlers.get(subscribe);

        if (handler != null) {
            handler.onSubscribe(user, session);
            if (!subscribes.containsKey(user)){
                subscribes.put(user, new ArrayList<>());
            }
            subscribes.get(user).add(subscribe);
            if (!sessionMap.containsKey(user)) {
                sessionMap.put(user, new ArrayList<>());
            }
            sessionMap.get(user).add(session);
        }
    }

    public void onClose(User user, Session session){
        if (sessionMap.containsKey(user)){
            ArrayList<Session> sessionList = this.sessionMap.get(user);
            sessionList.remove(session);
            if (sessionList.size() == 0){
                subscribes.remove(user);
                sessionMap.remove(user);
            }
        }
    }

    public void send(Subscribe subscribe, UpdateAction action, JsonAble jsonAble, User user) {
        ArrayList<Subscribe> subscribes = this.subscribes.get(user);
        if (subscribes != null) {
            for (Subscribe sub : subscribes) {
                if (sub == subscribe) {
                    JSONObject json = new JSONObject();
                    json.put(action.toString(), jsonAble.toJson());

                    JSONObject object = new JSONObject();
                    object.put(SUBSCRIBE, subscribe.toString());
                    object.put(DATA, json);

                    String s = object.toJSONString();
                    ArrayList<Session> sessions = sessionMap.get(user);
                    for (Session session : sessions) {
                        try {
                            if (session.isOpen()) {
                                session.getBasicRemote().sendText(s);
                            } else {
                                System.out.println("Session '" + session.getId() + "' is Close");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
