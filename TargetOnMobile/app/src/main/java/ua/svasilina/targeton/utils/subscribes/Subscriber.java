package ua.svasilina.targeton.utils.subscribes;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

import okhttp3.WebSocket;
import ua.svasilina.targeton.utils.connection.OkHttpSocket;

import static ua.svasilina.targeton.utils.constants.Constants.*;

public class Subscriber {

    private static final String LOG_TAG = "Subscriber";

    private static final Subscriber instance = new Subscriber();
    public static Subscriber getInstance(){
        return instance;
    }
    private WebSocket socket;
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
            json.put(SUBSCRIBE, subscribe.toString());
            json.put(USER, 1);
            Log.i(LOG_TAG, json.toString());

            if (socket == null){
                connect();
            }

            socket.send(json.toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void connect() {
        socket = new OkHttpSocket().connect();
    }

    public void handle(String text) {
        try {
            JSONObject json = new JSONObject(text);
            Subscribe subscribe = Subscribe.valueOf(String.valueOf(json.get(SUBSCRIBE)));
            if (handlerMap.containsKey(subscribe)){
                Handler handler = handlerMap.get(subscribe);
                if(handler != null) {
                    Message message = new Message();
                    JSONObject data = (JSONObject) json.get(DATA);

                    Iterator<String> keys = data.keys();
                    final Bundle bundle = message.getData();
                    while (keys.hasNext()) {
                        String next = keys.next();
                        Object val = data.get(next);
                        bundle.putString(next, String.valueOf(val));
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
