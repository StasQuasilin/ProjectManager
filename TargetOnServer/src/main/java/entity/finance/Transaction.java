package entity.finance;

import entity.user.User;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.Transient;
import java.sql.Date;

import static constants.Keys.*;

public class Transaction extends JsonAble {
    private int id;
    private Date date;
    private Category category;
    private Account account1;
    private Account account2;
    private float amount;
    private float rate;
    private String currency;
    private Counterparty counterparty;
    private TransactionType transactionType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Account getAccount1() {
        return account1;
    }

    public void setAccount1(Account account1) {
        this.account1 = account1;
    }

    public Account getAccount2() {
        return account2;
    }

    public void setAccount2(Account account2) {
        this.account2 = account2;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Counterparty getCounterparty() {
        return counterparty;
    }

    public void setCounterparty(Counterparty counterparty) {
        this.counterparty = counterparty;
    }

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
        if (account1 != null){
            jsonObject.put(ACCOUNT_FROM, account1.shortJson());
        }
        if (account2 != null){
            jsonObject.put(ACCOUNT_TO, account2.shortJson());
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
