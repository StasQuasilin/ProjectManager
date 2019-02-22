package services;

import java.util.HashMap;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class LanguageBase{

    static LanguageBase base = new LanguageBase();

    final String[] languages = {"ru"};
    final String baseName = "messages_";
    public static LanguageBase getBase() {
        return base;
    }

    final private HashMap<String, ResourceBundle> BUNDLES = new HashMap<>();

    public LanguageBase() {
        for (String l : languages){
            String resourceName = baseName + l;
            BUNDLES.put(l, ResourceBundle.getBundle(resourceName));
        }
    }

    public String get(String language, String key){
        try {
            return ResourceBundle.getBundle(baseName + language).getString(key);
        } catch (MissingResourceException e){
            return "%" + key + "%";
        }
    }
}
