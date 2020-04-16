package utils;

import constants.Keys;
import entity.transactions.TransactionCategory;
import entity.user.User;
import org.json.simple.JSONObject;
import services.hibernate.dbDAO;
import services.hibernate.dbDAOService;

/**
 * Created by szpt_user045 on 27.02.2020.
 */
public class CategoryUtil implements Keys {

    dbDAO dao = dbDAOService.getDao();

    public TransactionCategory getCategory(JSONObject json, User user){
        String categoryName = String.valueOf(json.get(NAME));
        TransactionCategory category = dao.getCategoryByName(categoryName, user);
        if (category == null) {
            category = new TransactionCategory();
            category.setName(categoryName);
            category.setOwner(user);
            dao.save(category);
        }

        return category;
    }
}
