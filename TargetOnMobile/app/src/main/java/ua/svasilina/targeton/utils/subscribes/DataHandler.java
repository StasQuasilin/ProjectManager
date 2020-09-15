package ua.svasilina.targeton.utils.subscribes;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ua.svasilina.targeton.utils.constants.Keys;
import ua.svasilina.targeton.utils.subscribes.updaters.DataUpdater;

public class DataHandler extends Handler {

    private final DataUpdater updater;

    public DataHandler(DataUpdater updater) {
        this.updater = updater;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        final Bundle data = msg.getData();

        if (data.containsKey(Keys.ADD)){
            final String add = data.getString(Keys.ADD);
            System.out.println(add);
            if (add != null){
                try {
                    JSONArray array = new JSONArray(add);
                    for (int i = 0; i < array.length(); i++){
                        final JSONObject object = array.getJSONObject(i);
                        updater.update(object);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        if (data.containsKey(Keys.UPDATE)){
            final String update = data.getString(Keys.UPDATE);
            System.out.println(update);
            try {
                if (update != null) {
                    updater.update(new JSONObject(update));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        updater.sort();
    }
}
