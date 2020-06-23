package entity.finance;

import java.sql.Date;

public class Transaction {
    private int id;
    private Date date;
    private Category category;
    private Account account1;
    private Account account2;
    private float amount;
    private float rate;
    private Currency currency;
    private TransactionType transactionType;
}
