package subscribe.handlers;

import entity.user.User;
import org.json.simple.JSONObject;
import subscribe.Subscribe;

import javax.websocket.Session;

import java.io.IOException;

import static constants.Keys.*;

public abstract class SubscribeHandler {

    private final Subscribe subscribe;

    protected SubscribeHandler(Subscribe subscribe) {
        this.subscribe = subscribe;
    }

    public Subscribe getSubscribe() {
        return subscribe;
    }

    public void onSubscribe(User user, Session session){
        Object items = getItems(user);
        if (items != null) {
            JSONObject data = new JSONObject();
            data.put(ADD, items);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(SUBSCRIBE, subscribe.toString());
            jsonObject.put(DATA, data);

            try {
                session.getBasicRemote().sendText(jsonObject.toJSONString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public abstract Object getItems(User user);
}
