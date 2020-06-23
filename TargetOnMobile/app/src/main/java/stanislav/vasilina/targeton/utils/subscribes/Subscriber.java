package stanislav.vasilina.targeton.utils.subscribes;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

import stanislav.vasilina.targeton.utils.connection.SocketConnector;

import static stanislav.vasilina.targeton.utils.constants.Constants.*;

public class Subscriber {

    private static final String LOG_TAG = "Subscriber";

    private static final Subscriber instance = new Subscriber();
    public static Subscriber getInstance(){
        return instance;
    }
    private SocketConnector connector;
    private Subscriber() {
        connector = new SocketConnector();
        connector.connect("ws://10.10.10.201:3322/ProjectManager/ProjectManager/socket");
    }

    private final HashMap<Subscribe, Handler> handlerMap = new HashMap<>();

    public void subscribe(Subscribe subscribe, Handler handler){
        handlerMap.put(subscribe, handler);
        sendAction(SubscribeAction.subscribe, subscribe);
    }

    public void unSubscribe(Subscribe subscribe){
        handlerMap.remove(subscribe);
        sendAction(SubscribeAction.unsubscribe, subscribe);
    }

    private void sendAction(SubscribeAction action, Subscribe subscribe){
        try {
            JSONObject json = new JSONObject();
            json.put(ACTION, action.toString());
            json.put(SUBSCRIBER, subscribe.toString());
            json.put(USER, 1);
            Log.i(LOG_TAG, json.toString());
            connector.send(json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void handle(String text) {
        Log.i("Subscriber", text);
        System.out.println(text);
        try {
            JSONObject json = new JSONObject(text);
            Subscribe subscribe = Subscribe.valueOf(String.valueOf(json.get(TYPE)));
            if (handlerMap.containsKey(subscribe)){
                Handler handler = handlerMap.get(subscribe);
                if(handler != null) {
                    Message message = new Message();
                    JSONObject data = (JSONObject) json.get(DATA);

                    Iterator<String> keys = data.keys();
                    while (keys.hasNext()) {
                        String next = keys.next();
                        Object val = data.get(next);
                        message.getData().putString(next, String.valueOf(val));
                    }
                    handler.sendMessage(message);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void closeAll(){

    }
}
