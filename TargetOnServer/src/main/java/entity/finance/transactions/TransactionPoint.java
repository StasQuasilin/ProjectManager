package entity.finance.transactions;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "transaction_points")
public class TransactionPoint {
    private int id;
    private Date date;
    private int transaction;
    private int account;
    private float amount;
    private float rate;
    private String currency;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "_date")
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "_transaction")
    public int getTransaction() {
        return transaction;
    }
    public void setTransaction(int transactionId) {
        this.transaction = transactionId;
    }

    @Basic
    @Column(name = "account")
    public int getAccount() {
        return account;
    }
    public void setAccount(int accountId) {
        this.account = accountId;
    }

    @Basic
    @Column(name = "amount")
    public float getAmount() {
        return amount;
    }
    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "rate")
    public float getRate() {
        return rate;
    }
    public void setRate(float rate) {
        this.rate = rate;
    }

    @Basic
    @Column(name = "currency")
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
