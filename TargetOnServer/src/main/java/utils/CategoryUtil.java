package utils;

import entity.finance.category.Category;
import entity.finance.category.Header;
import entity.user.User;
import utils.db.hibernate.Hibernator;
import utils.json.JsonObject;

import static constants.Keys.ID;
import static constants.Keys.TITLE;

public class CategoryUtil {

    private final Hibernator hibernator = Hibernator.getInstance();
    private final TitleUtil titleUtil = new TitleUtil();

    public Category getCategory(JsonObject data, User owner){
        Category category = hibernator.get(Category.class, ID, data.get(ID));

        if (category == null){

            final Header header = new Header();
            header.setOwner(owner);
            titleUtil.setHeaderName(header, data.getString(TITLE), owner);
            category = createCategory(header);

            hibernator.save(category);
        }

        return category;
    }

    private Category createCategory(Header header) {
        Category category = new Category();
        category.setHeader(header);
        hibernator.save(category);
        final Header parent = header.getParent();
        if (parent != null){
            createCategory(parent);
        }
        return category;
    }
}
