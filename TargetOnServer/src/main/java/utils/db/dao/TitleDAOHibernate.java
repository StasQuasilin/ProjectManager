package utils.db.dao;

import constants.Keys;
import entity.finance.category.Header;
import entity.user.User;

import java.util.HashMap;
import java.util.List;

import static constants.Keys.*;

public class TitleDAOHibernate extends TitleDAO {

    @Override
    public Header getHeader(Object id) {
        return hibernator.get(Header.class, Keys.ID, id);
    }

    @Override
    public void removeTitle(Object title) {
        hibernator.remove(title);
    }

    @Override
    public List<Header> findHeader(String key, User user) {
        final HashMap<String, Object> args = new HashMap<>();
        args.put(OWNER, user);
        return hibernator.find(Header.class, args, VALUE, key);
    }

    @Override
    public List<Header> getChildren(Header parent) {
        return hibernator.query(Header.class, PARENT, parent);
    }
}
