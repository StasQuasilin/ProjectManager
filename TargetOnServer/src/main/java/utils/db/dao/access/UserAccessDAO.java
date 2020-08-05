package utils.db.dao.access;

import entity.user.UserAccess;

public interface UserAccessDAO {
    UserAccess getUserAccess(Object email);
    void userRegistration(UserAccess userAccess);
}
