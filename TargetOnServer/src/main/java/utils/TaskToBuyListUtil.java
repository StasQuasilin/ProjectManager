package utils;

import entity.finance.buy.BuyList;
import entity.finance.buy.BuyListItem;
import entity.task.Task;
import utils.db.dao.daoService;
import utils.db.dao.finance.buy.BuyListDAO;
import utils.json.JsonObject;

import static constants.Keys.ID;
import static constants.Keys.TITLE;

public class TaskToBuyListUtil {

    private final BuyListDAO buyListDAO = daoService.getBuyListDAO();

    public void taskToBuyList(Task task, JsonObject buyList){
//        BuyListItem item = buyListDAO.getItemByCategory(task.getHeader());
//        if (item == null){
//            item = new BuyListItem();
//            item.setCategory(task.getHeader());
//            BuyList list = buyListDAO.getList(buyList.get(ID));
//            if (list == null){
//                list = new BuyList();
//                list.setOwner(task.getOwner());
//                list.setTitle(buyList.getString(TITLE));
//            }
//            item.setList(list);
//            list.addItem(item);
//            buyListDAO.saveList(list);
//        }
    }

    public void remove(Task task) {
//        final BuyListItem item = buyListDAO.getItemByCategory(task.getHeader());
//        if (item != null){
//            buyListDAO.removeItem(item);
//        }
    }
}
