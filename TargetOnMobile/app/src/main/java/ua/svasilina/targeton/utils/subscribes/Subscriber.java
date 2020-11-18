package ua.svasilina.targeton.utils.subscribes;

import android.content.Context;
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
import ua.svasilina.targeton.utils.constants.Keys;
import ua.svasilina.targeton.utils.storage.UserAccessStorage;

public class Subscriber {

    private static final String LOG_TAG = "Subscriber";

    private static final Subscriber instance = new Subscriber();
    public static Subscriber getInstance(){
        return instance;
    }
    private WebSocket socket;
    private final HashMap<Subscribe, Handler> handlerMap = new HashMap<>();
    private String userAccess;

    public void subscribe(Subscribe subscribe, Handler handler, Context context){
        handlerMap.put(subscribe, handler);
        if (userAccess == null){
            userAccess = UserAccessStorage.getUserAccess(context);
        }
        sendAction(SubscribeAction.subscribe, subscribe, userAccess);
    }

    public void unsubscribe(Subscribe subscribe){
        handlerMap.remove(subscribe);
        if (userAccess != null) {
            sendAction(SubscribeAction.unsubscribe, subscribe, userAccess);
        }
    }

    private void sendAction(SubscribeAction action, Subscribe subscribe, String userAccess){
        try {
            JSONObject json = new JSONObject();
            json.put(Keys.ACTION, action.toString());
            json.put(Keys.SUBSCRIBE, subscribe.toString());
            json.put(Keys.USER, userAccess);
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
            if (json.has(Keys.SUBSCRIBE)){
                Subscribe subscribe = Subscribe.valueOf(String.valueOf(json.get(Keys.SUBSCRIBE)));
                if (handlerMap.containsKey(subscribe)){
                    Handler handler = handlerMap.get(subscribe);
                    if(handler != null) {
                        Message message = new Message();
                        JSONObject data = (JSONObject) json.get(Keys.DATA);

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
            } else {
                System.out.println("What can i do with " + json.toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void closeAll(){

    }
}
