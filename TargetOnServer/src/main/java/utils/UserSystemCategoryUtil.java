package utils;

import entity.UserSystemCategory;
import entity.finance.transactions.Category;
import entity.user.User;
import utils.db.hibernate.Hibernator;

import static constants.Keys.OWNER;

public class UserSystemCategoryUtil {
    private final Hibernator hibernator = Hibernator.getInstance();
    private final LanguageBase languageBase = LanguageBase.getBase();

    public UserSystemCategory getCategories(User owner){
        UserSystemCategory userSystemCategory = hibernator.get(UserSystemCategory.class, OWNER, owner);
        if (userSystemCategory == null){
            userSystemCategory = new UserSystemCategory();
            userSystemCategory.setOwner(owner);
            Category create = createCategory(languageBase.get("transaction.account.create"), owner);
            hibernator.save(create);
            userSystemCategory.setCreateAccount(create);
            Category correction = createCategory(languageBase.get("transaction.account.correction"), owner);
            hibernator.save(correction);
            userSystemCategory.setAccountCorrection(correction);
            hibernator.save(userSystemCategory);
        }
        return userSystemCategory;
    }

    private Category createCategory(String title, User owner) {
        Category category = new Category();
        category.setTitle(title);
        category.setOwner(owner);
        category.setHidden(true);
        return category;
    }
}
