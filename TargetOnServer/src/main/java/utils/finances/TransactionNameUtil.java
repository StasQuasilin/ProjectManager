package utils.finances;

import constants.Keys;
import entity.finance.category.Header;
import entity.finance.transactions.Transaction;
import entity.finance.transactions.TransactionDetail;

import java.util.LinkedList;

import static constants.Keys.SPACE;

public class TransactionNameUtil {
    private static final int TITLE_LIMIT = 30;

    public boolean updateTransactionName(Transaction transaction, LinkedList<TransactionDetail> list, LinkedList<Header> headers) {
        StringBuilder builder = new StringBuilder();
        int totalCost = 0;
        int addedItems = 0;

        for (TransactionDetail detail : list){
            final Header header = detail.getHeader();
            if(!headers.contains(header)){
                headers.add(header);
            }
            final String title = header.getTitle();
            if (builder.length() + title.length() < TITLE_LIMIT){
                if (builder.length() > 0){
                    builder.append(Keys.COMMA).append(SPACE);
                }
                builder.append(title);
                addedItems++;
            }
            totalCost += detail.getPrice() * detail.getAmount();
        }
        int others = list.size() - addedItems;
        if (others > 0){
            builder.append("+").append(others);
        }
        final String description = builder.toString();
        boolean saveIt = false;
        if (transaction.getDescription() == null || !transaction.getDescription().equals(description)){
            transaction.setDescription(description);
            saveIt = true;
        }

        if (transaction.getAmount() != totalCost){
            transaction.setAmount(totalCost);
            saveIt = true;
        }

        return saveIt;
    }
}
