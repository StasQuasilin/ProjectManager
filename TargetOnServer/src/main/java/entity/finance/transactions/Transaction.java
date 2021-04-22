package entity.finance.transactions;

import entity.finance.Counterparty;
import entity.finance.Currency;
import entity.finance.accounts.Account;
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
    private String description;
    private float amount;
    private Currency currency;
    private float rate = 1;
    private Date date;
    private Account accountFrom;
    private Account accountTo;
    private Counterparty counterparty;
    private TransactionType transactionType;
    private User owner;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "_description")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "_amount")
    public float getAmount() {
        return amount;
    }
    public void setAmount(float amount) {
        this.amount = amount;
    }

    @OneToOne
    @JoinColumn(name = "_currency")
    public Currency getCurrency() {
        return currency;
    }
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Basic
    @Column(name = "_rate")
    public float getRate() {
        return rate;
    }
    public void setRate(float rate) {
        this.rate = rate;
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
    @JoinColumn(name = "_account_from")
    public Account getAccountFrom() {
        return accountFrom;
    }
    public void setAccountFrom(Account account) {
        this.accountFrom = account;
    }

    @OneToOne
    @JoinColumn(name = "_account_to")
    public Account getAccountTo() {
        return accountTo;
    }
    public void setAccountTo(Account account) {
        this.accountTo = account;
    }

    @OneToOne
    @JoinColumn(name = "_counterparty")
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

    @OneToOne
    @JoinColumn(name = "_owner")
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = getJsonObject();
        jsonObject.put(ID, id);
        jsonObject.put(DATE, date.toString());

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
        jsonObject.put(CURRENCY, currency.getName());
        jsonObject.put(RATE, rate);
        jsonObject.put(DESCRIPTION, description);
        jsonObject.put(AMOUNT, amount);

        return jsonObject;
    }
}
