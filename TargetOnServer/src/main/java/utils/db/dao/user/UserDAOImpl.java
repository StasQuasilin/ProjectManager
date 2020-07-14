package utils.db.dao.user;

import entity.user.User;
import utils.db.hibernate.Hibernator;
import static constants.Keys.ID;

public class UserDAOImpl implements UserDAO{

    private final Hibernator hibernator = Hibernator.getInstance();
    @Override
    public User getUserById(Object token) {
        return hibernator.get(User.class, ID, token);
    }
}
