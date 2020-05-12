package entity.transactions;

import constants.Keys;
import constants.TableNames;
import entity.budget.Account;
import entity.budget.Counterparty;
import entity.budget.Currency;
import entity.user.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonAble;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

/**
 * Created by szpt_user045 on 26.02.2020.
 */
@Entity
@Table(name = TableNames.TRANSACTIONS)
public class Transaction extends JsonAble implements Keys {
    private int id;
    private Timestamp dateTime;
    private TransactionCategory category;
    private TransactionType type;
    private Set<TransactionDetail> details;
    private Account account;
    private Account secondary;
    private Counterparty counterparty;
    private float amount;
    private float rate = 1;
    private Currency currency;
    private String comment;
    private User owner;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = TYPE)
    public TransactionType getType() {
        return type;
    }
    public void setType(TransactionType type) {
        this.type = type;
    }

//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "transaction", cascade = CascadeType.ALL)
    @Transient
    public Set<TransactionDetail> getDetails() {
        return details;
    }
    public void setDetails(Set<TransactionDetail> details) {
        this.details = details;
    }

    @Basic
    @Column(name = DATE)
    public Timestamp getDateTime() {
        return dateTime;
    }
    public void setDateTime(Timestamp date) {
        this.dateTime = date;
    }
    @Transient
    public Date getDate(){
        return Date.valueOf(dateTime.toLocalDateTime().toLocalDate());
    }
    public void setDate(Date date){
        LocalTime time;
        if (dateTime == null){
            time = LocalTime.now();
        } else {
            time = dateTime.toLocalDateTime().toLocalTime();
        }
        dateTime = Timestamp.valueOf(LocalDateTime.of(date.toLocalDate(), time));
    }

    @OneToOne
    @JoinColumn(name = COUNTERPARTY)
    public Counterparty getCounterparty() {
        return counterparty;
    }
    public void setCounterparty(Counterparty counterparty) {
        this.counterparty = counterparty;
    }

    @OneToOne
    @JoinColumn(name = "category")
    public TransactionCategory getCategory() {
        return category;
    }
    public void setCategory(TransactionCategory category) {
        this.category = category;
    }

    @OneToOne
    @JoinColumn(name = "account")
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }

    @OneToOne
    @JoinColumn(name = "secondary_account")
    public Account getSecondary() {
        return secondary;
    }
    public void setSecondary(Account secondary) {
        this.secondary = secondary;
    }

    @Basic
    @Column(name = "amount")
    public float getAmount() {
        return amount;
    }
    public void setAmount(float sum) {
        this.amount = sum;
    }

    @Basic
    @Column(name = "rate")
    public float getRate() {
        return rate;
    }
    public void setRate(float rate) {
        this.rate = rate;
    }

    @OneToOne
    @JoinColumn(name = CURRENCY)
    public Currency getCurrency() {
        return currency;
    }
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Basic
    @Column(name = COMMENT)
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    @OneToOne
    @JoinColumn(name = OWNER)
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }



    @Override
    public JSONObject shortJson() {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put(TYPE, type.toString());
        object.put(DATE, dateTime.toString());
        object.put(SUM, amount);
        object.put(RATE, rate);
        object.put(ACCOUNT, account.toJson());
        if(secondary != null){
            object.put(SECONDARY, secondary.toJson());
        }
        if (category != null) {
            object.put(CATEGORY, category.toJson());
        }

        if (currency != null) {
            object.put(CURRENCY, currency.shortJson());
        }
        return object;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = shortJson();
        if (comment != null) {
            object.put(COMMENT, comment);
        }
        if (counterparty != null){
            object.put(COUNTERPARTY, counterparty.toJson());
        }
        if (details != null) {
            object.put(DETAILS, details());
        }

        return object;
    }

    private JSONArray details() {
        JSONArray array = pool.getArray();
        for (TransactionDetail detail : details){
            array.add(detail.toJson());
        }
        return array;
    }

    @Transient
    public float getTotalSum() {
        return amount * rate;
    }
}
