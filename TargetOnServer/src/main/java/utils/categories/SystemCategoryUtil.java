package utils.categories;

import constants.Keys;
import entity.finance.category.CategoryType;
import entity.finance.category.Header;
import entity.finance.category.HeaderType;
import entity.finance.category.SystemCategory;
import org.apache.log4j.Logger;
import utils.LanguageBase;
import utils.db.hibernate.Hibernator;

import java.util.HashMap;

public class SystemCategoryUtil {

    private final LanguageBase languageBase = LanguageBase.getBase();
    private final Hibernator hibernator = Hibernator.getInstance();
    private final Logger logger = Logger.getLogger(SystemCategoryUtil.class);

    public void checkCategories() {
        final HashMap<String, Object> args = new HashMap<>();
        for (String lang : languageBase.getLocales()){
            args.put(Keys.LANGUAGE, lang);
            for (CategoryType type : CategoryType.values()){
                args.put(Keys.TYPE, type);
                final SystemCategory systemCategory = hibernator.get(SystemCategory.class, args);
                if(systemCategory == null){
                    final String key = Keys.TYPE + Keys.DOT + type;
                    final String value = languageBase.get(lang, key);
                    if(value != null){
                        Header header = new Header();
                        header.setType(HeaderType.category);
                        header.setValue(value);
                        hibernator.save(header);

                        SystemCategory category = new SystemCategory();
                        category.setLanguage(lang);
                        category.setType(type);
                        category.setHeader(header);
                        hibernator.save(category);
                    } else {
                        logger.warn("No key " + key + " for " + type + ", language " + lang);
                    }
                }
            }
        }
    }

    public Header getHeader(CategoryType type, String language) {
        final HashMap<String, Object> args = new HashMap<>();
        args.put(Keys.TYPE, type);
        args.put(Keys.LANGUAGE, language);
        final SystemCategory category = hibernator.get(SystemCategory.class, args);
        if(category != null){
            return category.getHeader();
        }
        return null;
    }
}
