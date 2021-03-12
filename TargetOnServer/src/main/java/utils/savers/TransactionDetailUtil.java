package utils.savers;

import entity.finance.category.Category;
import entity.finance.transactions.Transaction;
import entity.finance.transactions.TransactionDetail;
import entity.user.User;
import org.json.simple.JSONArray;
import utils.CategoryUtil;
import utils.db.dao.daoService;
import utils.db.dao.finance.transactions.TransactionDAO;
import utils.json.JsonObject;

import java.util.HashMap;
import java.util.LinkedList;

import static constants.Keys.*;

public class TransactionDetailUtil {

    private final CategoryUtil categoryUtil = new CategoryUtil();

    private final TransactionDAO transactionDAO = daoService.getTransactionDAO();

    public LinkedList<TransactionDetail> saveDetails(Transaction transaction, JSONArray details, User owner){
        final HashMap<Integer, TransactionDetail> map = new HashMap<>();

        for (TransactionDetail detail : transactionDAO.getDetails(transaction.getId())){
            map.put(detail.getId(), detail);
        }

        LinkedList<TransactionDetail> list = new LinkedList<>();

        for (Object o : details){
            JsonObject jsonObject = new JsonObject(o);
            System.out.println(jsonObject);

            TransactionDetail detail = map.remove(jsonObject.getInt(ID));
            if (detail == null){
                detail = new TransactionDetail();
                detail.setTransaction(transaction);
            }
            //todo get category by id
            //todo if category id -1 - create new category
            //todo if new category not equals detail category - change it
            final Category category = categoryUtil.getCategory(jsonObject.getJsonObject(HEADER), owner);
            detail.setCategory(category);

            detail.setAmount(jsonObject.getFloat(AMOUNT));
            detail.setPrice(jsonObject.getFloat(PRICE));

            transactionDAO.saveDetail(detail);
            list.add(detail);
        }
        transactionDAO.removeDetails(map.values());
        return list;
    }

    public void removeDetails(Transaction transaction) {
        transactionDAO.removeDetails(transactionDAO.getDetails(transaction));
    }
}
