package utils.db.dao.access;

import entity.user.UserAccess;
import utils.db.dao.daoService;
import utils.db.dao.user.UserDAO;
import utils.db.hibernate.Hibernator;

import static constants.Keys.LOGIN;

public class UserAccessDAOImpl implements UserAccessDAO {

    private final Hibernator hibernator = Hibernator.getInstance();
    private final UserDAO userDAO = daoService.getUserDAO();

    @Override
    public UserAccess getUserAccess(Object email) {
        return hibernator.get(UserAccess.class, LOGIN, email);
    }

    @Override
    public void userRegistration(UserAccess userAccess) {
        userDAO.save(userAccess.getUser());
        hibernator.save(userAccess);
    }
}
