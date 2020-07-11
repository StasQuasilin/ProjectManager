package utils.db.dao.finance.transactions;

import entity.finance.Account;
import entity.finance.Category;
import entity.finance.Transaction;
import entity.finance.TransactionType;
import entity.user.User;
import subscribe.Subscribe;
import utils.Updater;
import utils.db.dao.daoService;
import utils.db.dao.finance.accounts.AccountDAO;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by DELL on 09.07.2020.
 */
public class TransactionDAOPlug implements TransactionDAO {

    private final Updater updater = new Updater();
    private static final AccountDAO accountDAO = daoService.getAccountDAO();
    private static final HashMap<Integer, Transaction> transactions = new HashMap<>();
    static {
        Date date = Date.valueOf(LocalDate.now());
        Category category = new Category();
        category.setTitle("Test");
        for (Account account : accountDAO.getUserAccounts(null)){
            save(createTransaction(date, account, category));
        }
    }

    private static Transaction createTransaction(Date date, Account account, Category category) {
        Transaction transaction = new Transaction();

        transaction.setDate(date);
        transaction.setAccount1(account);
        transaction.setCategory(category);
        transaction.setTransactionType(TransactionType.payment);
        transaction.setAmount(1000);
        transaction.setCurrency("UAH");
        return transaction;
    }

    @Override
    public List<Transaction> getUserTransactions(User user) {
        return new ArrayList<>(transactions.values());
    }

    @Override
    public Transaction getTransaction(Object id) {
        if (id != null){
            int integer = Integer.parseInt(String.valueOf(id));
            return transactions.getOrDefault(integer, null);
        }
        return null;
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        save(transaction);
        updater.update(Subscribe.transactions, transaction, transaction.getOwner());
    }

    private static void save(Transaction transaction){
        if (transaction.getId() <= 0){
            transaction.setId(transactions.size() + 1);
        }
        transactions.put(transaction.getId(), transaction);
    }
}
