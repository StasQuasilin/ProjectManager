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
import java.util.Map;

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
        addHandler(new TimerHandler());
    }

    private void addHandler(SubscribeHandler handler) {
        handlers.put(handler.getSubscribe(), handler);
    }

    public static Subscriber getInstance() {
        return instance;
    }

    final HashMap<User, ArrayList<Session>> sessions = new HashMap<>();
    final HashMap<Session, ArrayList<Subscribe>> subscribes = new HashMap<>();

    public void subscribe(User user, Subscribe subscribe, Session session){

        SubscribeHandler handler = handlers.get(subscribe);

        if (handler != null) {
            if (!sessions.containsKey(user)){
                sessions.put(user, new ArrayList<>());
            }
            sessions.get(user).add(session);
            if(!subscribes.containsKey(session)){
                subscribes.put(session, new ArrayList<>());
            }
            subscribes.get(session).add(subscribe);
            handler.onSubscribe(user, session);
        } else {
            System.out.println("No handlers for " + subscribe);
        }
    }

    public void onClose(User user, Session session){
        final ArrayList<Session> remove = sessions.remove(user);
        remove.remove(session);
        subscribes.remove(session);
    }

    public void send(Subscribe subscribe, UpdateAction action, JsonAble jsonAble, User user) {
        final ArrayList<Session> sessions = this.sessions.get(user);
        if (sessions != null && sessions.size() > 0){
            JSONObject json = new JSONObject();

            json.put(action.toString(), jsonAble.toJson());

            JSONObject object = new JSONObject();
            object.put(SUBSCRIBE, subscribe.toString());
            object.put(DATA, json);

            final String data = object.toJSONString();

            for (Session session : sessions){
                for (Subscribe s : subscribes.get(session)){
                    if (s == subscribe){
                        try {
                            session.getBasicRemote().sendText(data);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public void unsubscribe(Session session, Subscribe subscribe) {
        final ArrayList<Subscribe> subscribes = this.subscribes.get(session);
        subscribes.remove(subscribe);
    }
}
