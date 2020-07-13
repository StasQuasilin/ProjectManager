package utils.db.dao.user;

import entity.user.User;

public interface UserDAO {
    User getUserById(Object token);

}
