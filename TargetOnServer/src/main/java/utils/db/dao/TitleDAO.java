package utils.db.dao;

import entity.finance.category.Header;
import utils.db.hibernate.Hibernator;

public abstract class TitleDAO {
    final Hibernator hibernator = Hibernator.getInstance();
    public abstract Header getHeader(Object id);
}
