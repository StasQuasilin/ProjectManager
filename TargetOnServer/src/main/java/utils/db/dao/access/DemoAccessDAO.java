package utils.db.dao.access;

import entity.user.DemoAccount;
import entity.user.User;

public interface DemoAccessDAO {
    DemoAccount getAccountByIp(String ip);
    DemoAccount getAccountByUser(User user);

    void save(DemoAccount demoAccount);

    void remove(User user);
}
