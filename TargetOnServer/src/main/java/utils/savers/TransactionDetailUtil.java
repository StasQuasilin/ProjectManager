package utils.savers;

import entity.finance.category.Category;
import entity.finance.category.Header;
import entity.finance.transactions.Transaction;
import entity.finance.transactions.TransactionDetail;
import entity.user.User;
import org.json.simple.JSONArray;
import utils.CategoryUtil;
import utils.db.dao.daoService;
import utils.db.dao.finance.transactions.TransactionDAO;
import utils.json.JsonObject;
import utils.removers.TransactionRemover;

import java.sql.Date;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static constants.Keys.*;

public class TransactionDetailUtil {

    private final CategoryUtil categoryUtil = new CategoryUtil();

    private final TransactionDAO transactionDAO = daoService.getTransactionDAO();
    private final TransactionRemover remover =new TransactionRemover();

    public LinkedList<TransactionDetail> saveDetails(Transaction transaction, JSONArray details, User owner){

        final HashMap<Integer, TransactionDetail> map = new HashMap<>();

        for (TransactionDetail detail : transactionDAO.getDetails(transaction.getId())){
            map.put(detail.getId(), detail);
        }

        LinkedList<TransactionDetail> list = new LinkedList<>();

        for (Object o : details){
            JsonObject jsonObject = new JsonObject(o);
            System.out.println(jsonObject);

            //Extract detail by id
            TransactionDetail detail = map.remove(jsonObject.getInt(ID));
            if (detail == null){
                detail = new TransactionDetail();
                detail.setTransaction(transaction);
            }

            //Extract header
            final int headerId = jsonObject.getInt(HEADER);
            final String title = jsonObject.getString(TITLE);
            final JSONArray path = jsonObject.getJsonArray(PATH);
            final Header header = categoryUtil.getCategory(headerId, title, path, owner);

            detail.setHeader(header);

            //Detail amount
            detail.setAmount(jsonObject.getFloat(AMOUNT));

            //Detail price
            detail.setPrice(jsonObject.getFloat(PRICE));

            transactionDAO.saveDetail(detail);
            list.add(detail);
        }

        //Remove other details
        removeDetails(map.values(), transaction.getDate());

        return list;
    }

    public void removeDetails(Transaction transaction) {
        removeDetails(transactionDAO.getDetails(transaction), transaction.getDate());
    }

    private void removeDetails(Collection<TransactionDetail> details, Date date) {
        for (TransactionDetail detail : details){
            remover.removeDetail(detail, date);
        }
    }
}
