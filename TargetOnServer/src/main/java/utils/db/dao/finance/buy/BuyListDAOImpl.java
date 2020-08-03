package utils.db.dao.finance.buy;

import entity.finance.buy.BuyList;
import entity.finance.buy.BuyListItem;
import entity.user.User;
import subscribe.Subscribe;
import utils.Updater;
import utils.db.hibernate.Hibernator;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static constants.Keys.*;

public class BuyListDAOImpl implements BuyListDAO {

    private final Hibernator hibernator = Hibernator.getInstance();
    private final Updater updater = new Updater();

    @Override
    public List<BuyList> getUserList(User user) {
        return hibernator.query(BuyList.class, OWNER, user);
    }

    @Override
    public BuyList getList(Object id) {
        return hibernator.get(BuyList.class, ID, id);
    }

    @Override
    public void saveList(BuyList list) {
        hibernator.save(list);
        for (BuyListItem item : list.getItemSet()){
            hibernator.save(item);
        }
        updater.update(Subscribe.buy, list, list.getOwner());

    }

    @Override
    public void removeItems(Collection<BuyListItem> items) {
        for(BuyListItem item : items){
            hibernator.remove(item);
        }
    }

    @Override
    public List<BuyList> findList(String key, User user) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put(OWNER, user);

        return hibernator.find(BuyList.class, params, TITLE, key);
    }
}
