package utils.db.dao;

import entity.finance.category.Header;
import entity.user.User;
import utils.db.hibernate.Hibernator;

import java.util.List;

public abstract class TitleDAO {
    final Hibernator hibernator = Hibernator.getInstance();
    public abstract Header getHeader(Object id);
    public abstract void removeTitle(Object title);
    public abstract List<Header> findHeader(String key, User user);
}
