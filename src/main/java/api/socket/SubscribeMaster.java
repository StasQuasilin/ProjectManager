package api.socket;

import api.socket.handlers.ISocketHandler;
import api.socket.handlers.MessageHandler;
import api.socket.handlers.ProjectHandler;
import entity.user.User;

import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 17.02.2020.
 */
public class SubscribeMaster {

    private static final SubscribeMaster master = new SubscribeMaster();

    public static SubscribeMaster getMaster() {
        return master;
    }

    private final HashMap<Subscribe, ISocketHandler> handlers = new HashMap<>();

    private SubscribeMaster() {
        handlers.put(Subscribe.messages, new MessageHandler(Subscribe.messages));
        handlers.put(Subscribe.projects, new ProjectHandler(Subscribe.projects));
    }

    public void subscribe(Subscribe subscribe, User user, Session session) throws IOException {
        ISocketHandler handler = handlers.get(subscribe);
        handler.subscribe(user, session);
    }

    public void unsubscribe(Subscribe subscribe, User user) {
        ISocketHandler handler = handlers.get(subscribe);
        handler.unsubscribe(user);
    }
}
