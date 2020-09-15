package subscribe;

import constants.ApiLinks;
import entity.user.User;
import utils.db.dao.user.UserDAO;
import utils.db.dao.user.UserDAOImpl;
import utils.json.JsonObject;
import utils.json.JsonParser;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.HashMap;

import static constants.Keys.*;

@ServerEndpoint(value = ApiLinks.SUBSCRIBE)
public class EndPoint {

    private final Subscriber subscriber = Subscriber.getInstance();
    private final JsonParser parser = new JsonParser();
    private final UserDAO userDAO = new UserDAOImpl();
    private final HashMap<Session, User> sessions = new HashMap<>();

    @OnMessage
    public void onMessage(Session session, String message){
        System.out.println(message);
        JsonObject parse = parser.parse(message);
        if (parse != null){
            if (parse.containKey(USER)) {
                Object id = parse.get(USER);
                User user = userDAO.getUserById(id);

                if (!sessions.containsKey(session)) {
                    sessions.put(session, user);
                }
                if (parse.containKey(ACTION)) {
                    SubscribeAction action = SubscribeAction.valueOf(parse.getString(ACTION));
                    Subscribe subscribe = Subscribe.valueOf(parse.getString(SUBSCRIBE));
                    switch (action) {
                        case subscribe:
                            subscriber.subscribe(user, subscribe, session);
                            break;
                        case unsubscribe:
                            subscriber.unsubscribe(session, subscribe);
                    }
                }
            }
        }
    }
    @OnError
    public void OnError(Session session, Throwable cause){
        cause.printStackTrace();
    }

    @OnClose
    public void OnClose(Session session){
        User user = sessions.remove(session);
        System.out.println("Session #" + session.getId() + " for user " + user + " closed");
        subscriber.onClose(user, session);
    }

}
