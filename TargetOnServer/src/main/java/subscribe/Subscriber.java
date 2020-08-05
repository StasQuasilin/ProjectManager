package subscribe;

import entity.user.User;
import org.json.simple.JSONObject;
import subscribe.handlers.*;
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
        addHandler(new TreeHandler());
        addHandler(new CalendarHandler());
        addHandler(new TransactionHandler());
        addHandler(new AccountHandler());
        addHandler(new BuyListHandler());
    }

    private void addHandler(SubscribeHandler handler) {
        handlers.put(handler.getSubscribe(), handler);
    }

    public static Subscriber getInstance() {
        return instance;
    }

    final HashMap<User, ArrayList<Subscribe>> subscribesMap = new HashMap<>();
    final HashMap<User, ArrayList<Session>> sessionMap = new HashMap<>();

    public void subscribe(User user, Subscribe subscribe, Session session){
        if (user != null){
            System.out.println("User '" + user + "' subscribe on " + subscribe.toString());
        }
        SubscribeHandler handler = handlers.get(subscribe);

        if (handler != null) {
            handler.onSubscribe(user, session);
            if (!subscribesMap.containsKey(user)){
                subscribesMap.put(user, new ArrayList<>());
            }
            subscribesMap.get(user).add(subscribe);
            if (!sessionMap.containsKey(user)) {
                sessionMap.put(user, new ArrayList<>());
            }
            sessionMap.get(user).add(session);
        } else {
            System.out.println("No handlers for " + subscribe);
        }
    }

    public void onClose(User user, Session session){
        if (sessionMap.containsKey(user)){
            ArrayList<Session> sessionList = this.sessionMap.get(user);
            sessionList.remove(session);
            if (sessionList.size() == 0){
                subscribesMap.remove(user);
                sessionMap.remove(user);
            }
        }
    }

    public void send(Subscribe subscribe, UpdateAction action, JsonAble jsonAble, User user) {
        ArrayList<Subscribe> subscribes = subscribesMap.get(user);
        if (subscribes != null) {
            JSONObject json = new JSONObject();
            json.put(action.toString(), jsonAble.toJson());

            JSONObject object = new JSONObject();
            object.put(SUBSCRIBE, subscribe.toString());
            object.put(DATA, json);

            final String s = object.toJSONString();

            for (Subscribe sub : subscribes) {
                if (sub == subscribe) {
                    ArrayList<Session> sessions = sessionMap.get(user);
                    ArrayList<Session> remove = new ArrayList<>();
                    for (Session session : sessions) {
                        try {
                            if (session.isOpen()) {
                                session.getBasicRemote().sendText(s);
                            } else {
                                System.out.println("Session '" + session.getId() + "' is Close");
                                remove.add(session);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    for (Session session : remove){
                        sessions.remove(session);
                    }
                    remove.clear();
                }
            }
        } else {
            System.out.println("No subscribes " + subscribe + " for " + user);
        }
    }

    public void unsubscribe(User user) {
        subscribesMap.remove(user);
        for (Session session : sessionMap.remove(user)){
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
