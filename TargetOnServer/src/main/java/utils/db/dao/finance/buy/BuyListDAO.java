package utils.db.dao.finance.buy;

import entity.finance.buy.BuyList;
import entity.finance.buy.BuyListItem;
import entity.finance.category.Header;
import entity.task.Task;
import entity.user.User;

import java.util.Collection;
import java.util.List;

public interface BuyListDAO {
    List<BuyList> getUserList(User user);
    BuyList getList(Object id);
    void saveList(BuyList list);

    BuyListItem getItemByHeader(Header header);

    void removeItems(Collection<BuyListItem> items);
    List<BuyList> findList(String key, User user);
    void removeItem(BuyListItem item);
    void removeList(BuyList list);

    BuyList getBaseList(Task task);
}
