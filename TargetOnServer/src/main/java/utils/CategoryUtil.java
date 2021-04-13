package utils;

import constants.Keys;
import entity.finance.category.Header;
import entity.finance.category.HeaderType;
import entity.user.User;
import org.json.simple.JSONArray;
import utils.db.hibernate.Hibernator;
import utils.json.JsonObject;

import static constants.Keys.ID;
import static constants.Keys.TITLE;

public class CategoryUtil {

    private final Hibernator hibernator = Hibernator.getInstance();
    private final TitleUtil titleUtil = new TitleUtil();

    public Header getCategory(int headerId, String title, JSONArray path, User owner){
        Header header = hibernator.get(Header.class, ID, headerId);
        if (header == null){
            header = new Header();
            header.setType(HeaderType.category);
            header.setOwner(owner);

            header.setValue(title.substring(0, 1).toUpperCase() + (title.length() > 1 ? title.substring(1) : Keys.EMPTY));
            if(path != null && path.size() > 0) {
                final JsonObject jo = new JsonObject(path.remove(path.size() - 1));
                final Header parent = getCategory(jo.getInt(ID), jo.getString(TITLE), path, owner);
                header.setParent(parent);
            }
            hibernator.save(header);
        }

        return header;
    }
}
