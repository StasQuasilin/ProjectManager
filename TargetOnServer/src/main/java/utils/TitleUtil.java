package utils;

import constants.Keys;
import entity.finance.category.Header;
import entity.finance.category.HeaderType;
import entity.user.User;
import utils.db.dao.TitleDAO;
import utils.db.dao.daoService;

import java.util.Arrays;

public class TitleUtil {

    TitleDAO titleDAO = daoService.getTitleDAO();

    public void setHeaderName(Header header, String title, User owner) {

        final String[] strings = title.split(Keys.SLASH);
        Header parent = header;
        Header prev = null;
        for (int i = strings.length - 1; i >= 0; i--){
            String s = strings[i];
            if (s.length() > 1){
                s = s.substring(0, 1).toUpperCase() + s.substring(1);
            }
            if (parent == null){
                parent = titleDAO.getHeaderByName(s);
            }
            if (parent == null){
                parent = new Header();
                parent.setType(HeaderType.category);
                parent.setOwner(owner);
            }
            parent.setValue(s);
            titleDAO.save(parent);

            if (prev != null){
                prev.setParent(parent);
                titleDAO.save(prev);
            }

            prev = parent;
            parent = parent.getParent();
        }
        System.out.println(Arrays.toString(strings));
    }
}
