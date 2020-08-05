package utils.db.dao.user;

import entity.user.User;
import entity.user.UserSettings;

public interface UserDAO {
    User getUserById(Object token);

    UserSettings getUserSettings(User user);
}
