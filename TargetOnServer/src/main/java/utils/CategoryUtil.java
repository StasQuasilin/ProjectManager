package utils;

import entity.finance.category.Header;
import entity.finance.category.HeaderType;
import entity.user.User;
import utils.db.hibernate.Hibernator;
import utils.json.JsonObject;

import static constants.Keys.ID;
import static constants.Keys.TITLE;

public class CategoryUtil {

    private final Hibernator hibernator = Hibernator.getInstance();
    private final TitleUtil titleUtil = new TitleUtil();

    public Header getCategory(int headerId, String title, User owner){
        Header header = hibernator.get(Header.class, ID, headerId);

        if (header == null){
            header = new Header();
            header.setType(HeaderType.category);
            header.setOwner(owner);
            titleUtil.setHeaderName(header, title, owner);
            hibernator.save(header);
        }

        return header;
    }
}
