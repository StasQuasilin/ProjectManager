package utils.db.dao.finance.buy;

import entity.finance.buy.BuyList;
import entity.finance.buy.BuyListItem;
import entity.finance.category.Header;
import entity.user.User;
import utils.db.hibernate.Hibernator;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static constants.Keys.*;

public class BuyListDAOImpl implements BuyListDAO {

    private final Hibernator hibernator = Hibernator.getInstance();


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
        hibernator.save(list.getTitle());
        hibernator.save(list);
    }

    @Override
    public void saveItems(Set<BuyListItem> itemSet) {
        for (BuyListItem item : itemSet){
            hibernator.save(item.getHeader());
            hibernator.save(item);
        }

    }

    @Override
    public List<BuyList> findList(String key, User user) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put(OWNER, user);

        return hibernator.find(BuyList.class, params, HEADER, key);
    }

    @Override
    public BuyListItem getItemByHeader(Header header) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put(HEADER, header);
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

    @Override
    public BuyList getBaseList(Header header) {
        Header parent = null;
        Header p = header;
        while (p != null){
            parent = p;
            p = p.getParent();
        }
        if (parent != null){
            return getListByHeader(parent.getId());
        } else {
            return null;
        }
    }

    private BuyList getListByHeader(int id) {
        return hibernator.get(BuyList.class, TITLE, id);
    }
}
