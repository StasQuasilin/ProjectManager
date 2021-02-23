package utils;

import entity.finance.category.Category;
import entity.user.User;
import utils.db.hibernate.Hibernator;
import utils.json.JsonObject;

import static constants.Keys.ID;
import static constants.Keys.TITLE;

public class CategoryUtil {
    private final Hibernator hibernator = Hibernator.getInstance();

    public Category getCategory(JsonObject data, User owner){
        Category category = hibernator.get(Category.class, ID, data.get(ID));


        if (category == null){
            category = new Category();
//            category.setOwner(owner);
        }

        String title = data.getString(TITLE);
//        if (title.length() > 0){
//            category.setTitle(title.substring(0, 1).toUpperCase() + title.substring(1).toLowerCase());
//        } else {
//            category.setTitle(title);
//        }

        hibernator.save(category);

        return category;
    }
}
