package controllers.api.finance.transactions;

import constants.ApiLinks;
import constants.Keys;
import controllers.api.API;
import entity.finance.Currency;
import entity.finance.UserCurrency;
import entity.finance.accounts.Account;
import entity.finance.category.Header;
import entity.finance.transactions.Transaction;
import entity.finance.transactions.TransactionDetail;
import entity.finance.transactions.TransactionType;
import entity.user.User;
import org.json.simple.JSONArray;
import utils.UserSystemCategoryUtil;
import utils.db.dao.daoService;
import utils.db.dao.finance.accounts.AccountDAO;
import utils.db.dao.finance.currency.CurrencyDAO;
import utils.db.dao.finance.transactions.TransactionDAO;
import utils.finances.TransactionUtil;
import utils.json.JsonObject;
import utils.savers.TransactionDetailUtil;
import utils.savers.TransactionSaver;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.LinkedList;

import static constants.Keys.*;

@WebServlet(ApiLinks.TRANSACTION_SAVE)
public class EditTransactionAPI extends API {

    private static final int TITLE_LIMIT = 30;
    private final AccountDAO accountDAO = daoService.getAccountDAO();
    private final TransactionSaver transactionSaver = new TransactionSaver();
    private final TransactionDAO transactionDAO = daoService.getTransactionDAO();
    private final TransactionUtil transactionUtil = new TransactionUtil();
    private final UserSystemCategoryUtil uscu = new UserSystemCategoryUtil();
    private final TransactionDetailUtil tdu = new TransactionDetailUtil();
    private final CurrencyDAO currencyDAO = daoService.getCurrencyDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JsonObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            final User user = getUser(req);
            Transaction transaction = transactionDAO.getTransaction(body.get(ID));
            if (transaction == null){
                transaction = new Transaction();
                transaction.setOwner(user);
            }

            TransactionType type = TransactionType.valueOf(body.getString(TYPE));
            transaction.setTransactionType(type);

            Date prevDate = transaction.getDate();
            Date date = Date.valueOf(body.getString(DATE));
            transaction.setDate(date);

            String currencyName = body.getString(CURRENCY);
            final Currency currency = currencyDAO.getCurrency(currencyName);
            transaction.setCurrency(currency);
            UserCurrency userCurrency = currencyDAO.getUserCurrency(currency, user);
            if (userCurrency == null){
                userCurrency = new UserCurrency();
                userCurrency.setUser(user);
                userCurrency.setCurrency(currency);
                currencyDAO.saveUserCurrency(userCurrency);
            }

            Account prevAccountFrom = null;
            if (body.containKey(ACCOUNT_FROM)){
                final Account account = accountDAO.getAccount(body.get(ACCOUNT_FROM));
                prevAccountFrom = transaction.getAccountFrom();
                transaction.setAccountFrom(account);
            } else {
                transaction.setAccountFrom(null);
            }
            Account prevAccountTo = null;
            if (body.containKey(ACCOUNT_TO)){
                final Account account = accountDAO.getAccount(body.get(ACCOUNT_TO));
                prevAccountTo = transaction.getAccountTo();
                transaction.setAccountTo(account);
            } else {
                transaction.setAccountTo(null);
            }

            if (type == TransactionType.transfer){
                int amount = body.getInt(AMOUNT);
                transaction.setAmount(amount);
                body.remove(DETAILS);
            }

            write(resp, SUCCESS_ANSWER);
            transactionSaver.save(transaction);

            if(body.containKey(DETAILS)) {
                final LinkedList<TransactionDetail> list = tdu.saveDetails(transaction, (JSONArray) body.get(DETAILS), user);
                StringBuilder builder = new StringBuilder();
                int totalCost = 0;
                int addedItems = 0;
                LinkedList<Header> headers = new LinkedList<>();
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
                    builder.append(PLUS).append(others);
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
                if(saveIt){
                    transactionSaver.save(transaction);
                    updateHeadersCoasts(headers, date);
                }
            } else {
                tdu.removeDetails(transaction);
            }

//            if (type == TransactionType.income || type == TransactionType.spending) {
//                if (prevCategory != null && !prevCategory.equals(transaction.getCategory())) {
//                    transactionUtil.removePoint(prevCategory, transaction.getDate());
//                }
//            }
            if (prevDate != null && !prevDate.equals(transaction.getDate())){

                if (prevAccountFrom != null){
                    transactionUtil.removeTransactionPoint(prevAccountFrom, transaction.getId(), prevDate);
                }
                if (prevAccountTo != null){
                    transactionUtil.removeTransactionPoint(prevAccountTo, transaction.getId(), prevDate);
                }
//                transactionUtil.updateCategory(transaction.getCategory(), prevDate);
            }
            if (prevAccountFrom != null && prevAccountFrom.getId() != transaction.getAccountFrom().getId()){
                transactionUtil.removeTransactionPoint(prevAccountFrom, transaction.getId(), transaction.getDate());
            }
            if (prevAccountTo != null && prevAccountTo.getId() != transaction.getAccountTo().getId()){
                transactionUtil.removeTransactionPoint(prevAccountTo, transaction.getId(), transaction.getDate());
            }
//            if (prevAccountFrom != null && !prevAccountFrom.equals(transaction.getAccountFrom())){
//                transactionUtil.removePoint(transaction, prevAccountFrom);
//            }
//            if (prevAccountTo != null && !prevAccountTo.equals(transaction.getAccountTo())){
//                transactionUtil.removePoint(transaction, prevAccountTo);
//            }
        }
    }

    private void updateHeadersCoasts(LinkedList<Header> headers, Date date) {
        LinkedList<Header> parents = new LinkedList<>();
        for (Header header : headers){
            System.out.println(header);
            transactionUtil.updateCategoryDay(header, date);
            final Header parent = header.getParent();
            if (parent != null && !parents.contains(parent)){
                parents.add(parent);
            }
        }
        if (parents.size() > 0) {
            updateHeadersCoasts(parents, date);
        }
    }
}
