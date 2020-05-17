package api.socket;

import api.socket.handlers.*;
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
        addHandler(new MessageHandler(Subscribe.messages));
        addHandler(new ProjectHandler(Subscribe.projects));
        addHandler(new CalendarHandler(Subscribe.calendar));
        addHandler(new AccountHandlet(Subscribe.accounts));
        addHandler(new TransactionHadler(Subscribe.transactions));
        addHandler(new TreeHandler(Subscribe.tree));
        addHandler(new KanbanHandler(Subscribe.kanban));
        addHandler(new BuyListHandler(Subscribe.buyList));
        addHandler(new FastHandler(Subscribe.fast));
    }

    private void addHandler(ISocketHandler handler){
        handlers.put(handler.getSubscribe(), handler);
    }

    public void subscribe(Subscribe subscribe, User user, Session session) throws IOException {
        if (handlers.containsKey(subscribe)) {
            ISocketHandler handler = handlers.get(subscribe);
            handler.subscribe(user, session);
        }
    }

    public void unsubscribe(Subscribe subscribe, User user) {
        if (handlers.containsKey(subscribe)) {
            ISocketHandler handler = handlers.get(subscribe);
            handler.unsubscribe(user);
        }
    }

    public ISocketHandler getHandler(Subscribe subscribe){
        return handlers.get(subscribe);
    }
}
