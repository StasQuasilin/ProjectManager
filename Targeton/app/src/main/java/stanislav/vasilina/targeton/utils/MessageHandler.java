package stanislav.vasilina.targeton.utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class MessageHandler {

    private static MessageHandler handler = new MessageHandler();
    public static MessageHandler getInstance(){
        return handler;
    }

    public void handleMessage(String message){
        Log.i(MessageHandler.class.getName(), message);
    }



}
