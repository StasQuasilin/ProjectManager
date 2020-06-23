package subscribe;

import entity.user.User;
import subscribe.handlers.GoalHandler;
import subscribe.handlers.SubscribeHandler;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.HashMap;

public final class Subscriber {

    private static final Subscriber instance = new Subscriber();
    private final HashMap<Subscribe, SubscribeHandler> handlers = new HashMap<>();
    {
        addHandler(new GoalHandler());
    }

    private void addHandler(SubscribeHandler handler) {
        handlers.put(handler.getSubscribe(), handler);
    }

    public static Subscriber getInstance() {
        return instance;
    }

    final HashMap<User, Subscribe> subscribes = new HashMap<>();
    final HashMap<User, ArrayList<Session>> sessionMap = new HashMap<>();

    public void subscribe(User user, Subscribe subscribe, Session session){
        SubscribeHandler handler = handlers.get(subscribe);
        if (handler != null) {
            System.out.println("+");
            handler.onSubscribe(user, session);
            subscribes.put(user, subscribe);
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
}
