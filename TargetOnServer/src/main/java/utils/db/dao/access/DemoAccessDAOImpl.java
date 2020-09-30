package utils.db.dao.access;

import entity.user.DemoAccount;
import entity.user.User;
import utils.db.hibernate.Hibernator;

public class DemoAccessDAOImpl implements DemoAccessDAO {

    private final Hibernator hibernator = Hibernator.getInstance();

    @Override
    public DemoAccount getAccountByIp(String ip) {
        return hibernator.get(DemoAccount.class, "ip", ip);
    }

    @Override
    public DemoAccount getAccountByUser(User user) {
        return hibernator.get(DemoAccount.class, "user", user);
    }

    @Override
    public void save(DemoAccount demoAccount) {
        hibernator.save(demoAccount);
    }

    @Override
    public void remove(User user) {
        hibernator.remove(DemoAccount.class, "user", user);
    }
}
