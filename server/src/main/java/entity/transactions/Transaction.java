package entity.transactions;

import constants.Keys;
import constants.Tables;
import entity.budget.Budget;
import entity.budget.Counterparty;
import entity.budget.Currency;
import entity.user.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonAble;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

/**
 * Created by szpt_user045 on 26.02.2020.
 */
@Entity
@Table(name = Tables.TRANSACTIONS)
public class Transaction extends JsonAble implements Keys {
    private int id;
    private Date date;
    private TransactionCategory category;
    private TransactionType type;
    private Set<TransactionDetail> details;
    private Budget budget;
    private Counterparty counterparty;
    private float sum;
    private float rate = 1;
    private Currency currency;
    private String comment;
    private User owner;
    private Transaction child;

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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "transaction", cascade = CascadeType.ALL)
    public Set<TransactionDetail> getDetails() {
        return details;
    }
    public void setDetails(Set<TransactionDetail> details) {
        this.details = details;
    }

    @Basic
    @Column(name = DATE)
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
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
    @JoinColumn(name = CATEGORY)
    public TransactionCategory getCategory() {
        return category;
    }
    public void setCategory(TransactionCategory category) {
        this.category = category;
    }

    @OneToOne
    @JoinColumn(name = BUDGET)
    public Budget getBudget() {
        return budget;
    }
    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    @Basic
    @Column(name = SUM)
    public float getSum() {
        return sum;
    }
    public void setSum(float sum) {
        this.sum = sum;
    }

    @Basic
    @Column(name = RATE)
    public float getRate() {
        return rate;
    }
    public void setRate(float rate) {
        this.rate = rate;
    }

    @Transient
    public float getTotalSum(){
        return sum * rate;
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

    @OneToOne
    @JoinColumn(name = "child")
    public Transaction getChild() {
        return child;
    }
    public void setChild(Transaction child) {
        this.child = child;
    }

    @Override
    public JSONObject shortJson() {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put(TYPE, type.toString());
        object.put(DATE, date.toString());
        object.put(SUM, sum);
        object.put(RATE, rate);
        object.put(ACCOUNT, budget.toJson());
        if (currency != null) {
            object.put(CURRENCY, currency.toJson());
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
        if (child != null){
            object.put(CHILDREN, child.shortJson());
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
}
