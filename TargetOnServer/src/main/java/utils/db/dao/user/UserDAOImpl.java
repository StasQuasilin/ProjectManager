package utils.db.dao.user;

import entity.user.User;
import entity.user.UserSettings;
import utils.db.hibernate.Hibernator;
import static constants.Keys.ID;
import static constants.Keys.USER;

public class UserDAOImpl implements UserDAO{

    private final Hibernator hibernator = Hibernator.getInstance();
    @Override
    public User getUserById(Object token) {
        return hibernator.get(User.class, ID, token);
    }

    @Override
    public UserSettings getUserSettings(User user) {
        return hibernator.get(UserSettings.class, USER, user);
    }
}
