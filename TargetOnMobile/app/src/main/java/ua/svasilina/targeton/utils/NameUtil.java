package ua.svasilina.targeton.utils;

import android.content.Context;
import android.content.res.Resources;

public class NameUtil {
    public static String getNameByString(Context context, String key){
        final Resources resources = context.getResources();
        final String packageName = context.getPackageName();
        final String defType = "string";
        final int identifier = resources.getIdentifier(key, defType, packageName);
        if (identifier != 0){
            return resources.getString(identifier);
        } else {
            return key;
        }
    }
}
