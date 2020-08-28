package ua.svasilina.targeton.utils.subscribes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static ua.svasilina.targeton.utils.constants.Constants.ADD;
import static ua.svasilina.targeton.utils.constants.Constants.DATA;
import static ua.svasilina.targeton.utils.constants.Constants.SUBSCRIBE;

public class SubscribeHandler {

    public void handle(String message){
        try {
            final JSONObject jsonObject = new JSONObject(message);
            System.out.println(jsonObject.getString(SUBSCRIBE));
            final JSONObject data = jsonObject.getJSONObject(DATA);
            if (data.has(ADD)){
                final JSONArray add = data.getJSONArray(ADD);
                for (int i = 0; i < add.length(); i++){
                    System.out.println("\t" + add.get(i));
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
