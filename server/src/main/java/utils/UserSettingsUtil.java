package utils;

import entity.transactions.TransactionCategory;
import entity.user.User;
import entity.user.UserSettings;
import services.LanguageBase;
import services.hibernate.dbDAO;
import services.hibernate.dbDAOService;

public class UserSettingsUtil {
    private static final dbDAO dao = dbDAOService.getDao();
    private static final LanguageBase lang = LanguageBase.getBase();
    public static UserSettings getUserSettings(User user){
        UserSettings userSettings = dao.getUserSettings(user);
        if (userSettings == null){
            userSettings = new UserSettings();
            userSettings.setUser(user);
            userSettings.setCorrectionCategory(createCategory(user, lang.get(user.getLanguage(), "category.correction")));
            userSettings.setOpenCategory(createCategory(user, lang.get(user.getLanguage(), "category.open")));
            dao.save(userSettings);
        }
        return userSettings;
    }
    private static TransactionCategory createCategory(User user, String name){
        TransactionCategory category = new TransactionCategory();
        category.setOwner(user);
        category.setName(name);
        dao.save(category);
        return category;
    }
}
