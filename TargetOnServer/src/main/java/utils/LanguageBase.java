package utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;

public class LanguageBase{

    static LanguageBase base = new LanguageBase();

    public static final String[] LANGUAGES = {"uk"};
    public static final String DEFAULT_LANGUAGE = LANGUAGES[0];
    final static String BASE_NAME = "messages_";
    public static LanguageBase getBase() {
        return base;
    }

    public String getOrDefault(String key){
        return getOrDefault(DEFAULT_LANGUAGE, key);
    }
    public String getOrDefault(String language, String key){
        final String value = get(language, key);
         if(value != null) {
            return value;
        } else {
             return "???" + key + "???";
         }
    }
    public String get(String language, String key){
        if (language == null){
            language = DEFAULT_LANGUAGE;
        }
        ResourceBundle bundle = ResourceBundle.getBundle(BASE_NAME + language);
        if (bundle.containsKey(key)){
            return bundle.getString(key);
        }
        return null;
    }


    public String getLocale(String lang) {
        final HashSet<String> strings = new HashSet<>(Arrays.asList(LANGUAGES));
        if (strings.contains(lang)){
            return lang;
        }
        return DEFAULT_LANGUAGE;
    }

    public String[] getLocales() {
        return LANGUAGES;
    }

    public String getDefaultLocale() {
        return DEFAULT_LANGUAGE;
    }
}
