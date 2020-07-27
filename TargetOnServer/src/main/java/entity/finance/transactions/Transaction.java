package entity.finance.transactions;

import entity.finance.Counterparty;
import entity.finance.accounts.Account;
import entity.finance.category.Category;
import entity.user.User;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;
import java.sql.Date;

import static constants.Keys.*;

@Entity
@Table(name = "transactions")
public class Transaction extends JsonAble {
    private int id;
    private Date date;
    private Category category;
    private Account accountFrom;
    private Account accountTo;
    private float amount;
    private float rate;
    private String currency;
    private Counterparty counterparty;
    private TransactionType transactionType;

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

    @OneToOne
    @JoinColumn(name = "_category")
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    @OneToOne
    @JoinColumn(name = "_account_from")
    public Account getAccountFrom() {
        return accountFrom;
    }
    public void setAccountFrom(Account account1) {
        this.accountFrom = account1;
    }

    @OneToOne
    @JoinColumn(name = "_account_to")
    public Account getAccountTo() {
        return accountTo;
    }
    public void setAccountTo(Account account2) {
        this.accountTo = account2;
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

//    @OneToOne
//    @JoinColumn(name = "counterparty")
    @Transient
    public Counterparty getCounterparty() {
        return counterparty;
    }
    public void setCounterparty(Counterparty counterparty) {
        this.counterparty = counterparty;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "_type")
    public TransactionType getTransactionType() {
        return transactionType;
    }
    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = getJsonObject();
        jsonObject.put(ID, id);
        jsonObject.put(DATE, date.toString());
        jsonObject.put(CATEGORY, category.shortJson());

        if (accountFrom != null){
            jsonObject.put(ACCOUNT_FROM, accountFrom.shortJson());
        }
        if (accountTo != null){
            jsonObject.put(ACCOUNT_TO, accountTo.shortJson());
        }
        if (counterparty != null){
            jsonObject.put(COUNTERPARTY, counterparty.shortJson());
        }
        jsonObject.put(TYPE, transactionType.toString());
        jsonObject.put(AMOUNT, amount);
        jsonObject.put(CURRENCY, currency);
        jsonObject.put(RATE, rate);

        return jsonObject;
    }

    @Transient
    public User getOwner() {
        return category.getOwner();
    }
}
