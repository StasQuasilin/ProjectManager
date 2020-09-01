package ua.svasilina.targeton.utils.storage;

import android.content.Context;
import android.content.SharedPreferences;

import static ua.svasilina.targeton.utils.constants.Keys.KEY;

public class UserDataStorage {
    static final String USER_DATA = "user_data";

    public static String getUserData(Context context){
        return getSharedPreferences(context).getString(KEY, null);
    }

    static SharedPreferences getSharedPreferences(Context ctx){
        return ctx.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
    }

    public static void saveUserData(Context ctx, String data){
        final SharedPreferences sharedPreferences = getSharedPreferences(ctx);
        if (sharedPreferences != null) {
            final SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString(KEY, data);
            edit.apply();
        }
    }
}
