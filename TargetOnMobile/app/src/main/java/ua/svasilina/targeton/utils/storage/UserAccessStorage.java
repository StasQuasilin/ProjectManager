package ua.svasilina.targeton.utils.storage;

import android.content.Context;
import android.content.SharedPreferences;

import static ua.svasilina.targeton.utils.constants.Keys.KEY;

public class UserAccessStorage {

    static final String USER_ACCESS = "user_access";

    static SharedPreferences getSharedPreferences(Context ctx){
        return ctx.getSharedPreferences(USER_ACCESS, Context.MODE_PRIVATE);
    }

    public static String getUserAccess(Context ctx){
        return getSharedPreferences(ctx).getString(KEY, null);
    }

    public static void saveUserAccess(Context ctx, String access){
        final SharedPreferences sharedPreferences = getSharedPreferences(ctx);
        if (sharedPreferences != null) {
            final SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString(KEY, access);
            edit.apply();
        }
    }
}
