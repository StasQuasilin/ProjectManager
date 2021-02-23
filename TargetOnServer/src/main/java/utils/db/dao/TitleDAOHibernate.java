package utils.db.dao;

import constants.Keys;
import entity.finance.category.Header;

public class TitleDAOHibernate extends TitleDAO {

    @Override
    public Header getHeader(Object id) {
        return hibernator.get(Header.class, Keys.ID, id);
    }
}
