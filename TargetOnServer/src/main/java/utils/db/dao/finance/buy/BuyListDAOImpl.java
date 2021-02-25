package utils.db.dao.finance.buy;

import entity.finance.buy.BuyList;
import entity.finance.buy.BuyListItem;
import entity.finance.category.Category;
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
        return hibernator.query(BuyList.class, TITLE_OWNER, user);
    }

    @Override
    public BuyList getList(Object id) {
        return hibernator.get(BuyList.class, ID, id);
    }

    @Override
    public void saveList(BuyList list) {
        for (BuyListItem item : list.getItemSet()){
            hibernator.save(item.getCategory());
            hibernator.save(item);
        }
        hibernator.save(list);
        updater.update(Subscribe.buy, list, list.getOwner());
    }

    @Override
    public List<BuyList> findList(String key, User user) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put(OWNER, user);

        return hibernator.find(BuyList.class, params, TITLE, key);
    }

    @Override
    public BuyListItem getItemByCategory(Category category) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put(CATEGORY, category);

        return hibernator.get(BuyListItem.class, params);
    }

    @Override
    public void removeItems(Collection<BuyListItem> items) {
        for(BuyListItem item : items){
            removeItem(item);
        }
    }

    @Override
    public void removeItem(BuyListItem item) {
        hibernator.remove(item);
    }

    @Override
    public void removeList(BuyList list) {
        removeItems(list.getItemSet());
        hibernator.remove(list);
    }
}
