package utils;

import entity.finance.transactions.Transaction;
import utils.db.dao.daoService;
import utils.db.dao.finance.buy.BuyListDAO;
import utils.db.dao.tree.TaskDAO;
import utils.savers.TaskSaver;

public class BuyListChecker {
    private final BuyListDAO buyListDAO = daoService.getBuyListDAO();
    private final TaskDAO taskDAO = daoService.getTaskDAO();
    private final TaskSaver taskSaver = new TaskSaver();

    public void check(Transaction transaction) {
//        final Category category = transaction.getCategory();
//        final BuyListItem itemByCategory = buyListDAO.getItemByCategory(category);
//        if (itemByCategory != null){
//            itemByCategory.setPrice(transaction.getAmount());
//            itemByCategory.setCurrency(transaction.getCurrency());
//
//            itemByCategory.setDone(true);
//            final BuyList list = itemByCategory.getList();
//            list.addItem(itemByCategory);
//            buyListDAO.saveList(list);
//        }
//        final Task taskByCategory = taskDAO.getTaskByHeader(category);
//        if (taskByCategory != null) {
//            taskByCategory.setStatus(TaskStatus.done);
//            taskSaver.save(taskByCategory);
//        }
    }
}
