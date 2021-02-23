package utils.savers;

import entity.finance.category.Category;
import entity.finance.transactions.Transaction;
import entity.finance.transactions.TransactionDetail;
import org.json.simple.JSONArray;
import utils.db.dao.category.CategoryDAO;
import utils.db.dao.daoService;
import utils.db.dao.finance.transactions.TransactionDAO;
import utils.json.JsonObject;

import java.util.HashMap;
import java.util.List;

import static constants.Keys.*;

public class TransactionDetailUtil {

    private final TransactionDAO transactionDAO = daoService.getTransactionDAO();
    private final CategoryDAO categoryDAO = daoService.getCategoryDAO();

    public void saveDetails(Transaction transaction, JSONArray details){
        final HashMap<Integer, TransactionDetail> map = new HashMap<>();
        for (TransactionDetail detail : transactionDAO.getDetails(transaction.getId())){
            map.put(detail.getId(), detail);
        }

        for (Object o : details){
            JsonObject jsonObject = new JsonObject(o);
            TransactionDetail detail = map.remove(jsonObject.getInt(ID));
            if (detail == null){
                detail = new TransactionDetail();
                detail.setTransaction(transaction);
            }

            Category category = detail.getCategory();
            if (category == null){
                category = categoryDAO.getCategory(jsonObject.get(CATEGORY));
                if (category == null){
                    category = new Category();
//                    category.setParent(transaction.getCategory());
                }
                detail.setCategory(category);
            }
//            category.setTitle(jsonObject.getString(TITLE));
            detail.setAmount(jsonObject.getFloat(AMOUNT));
            detail.setPrice(jsonObject.getFloat(PRICE));

            transactionDAO.saveDetail(detail);
        }
        transactionDAO.removeDetails(map.values());
    }

    public void removeDetails(Transaction transaction) {
        transactionDAO.removeDetails(transactionDAO.getDetails(transaction));
    }
}
