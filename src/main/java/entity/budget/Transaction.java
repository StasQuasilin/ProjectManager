package entity.budget;

import constants.Keys;
import constants.Tables;
import entity.user.Person;
import entity.user.User;
import org.json.simple.JSONObject;
import utils.JsonAble;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by szpt_user045 on 26.02.2020.
 */
@Entity
@Table(name = Tables.TRANSACTIONS)
public class Transaction implements Keys, JsonAble {
    private int id;
    private TransactionType type;
    private Date date;
    private TransactionCategory category;
    private Person payer;
    private Person payee;
    private Budget budget;
    private float sum;
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

    @Basic
    @Column(name = DATE)
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
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
    @JoinColumn(name = PAYER)
    public Person getPayer() {
        return payer;
    }
    public void setPayer(Person payer) {
        this.payer = payer;
    }

    @OneToOne
    @JoinColumn(name = PAYEE)
    public Person getPayee() {
        return payee;
    }
    public void setPayee(Person payee) {
        this.payee = payee;
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
    public JSONObject toJson() {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put(TYPE, type.toString());
        object.put(DATE, date.toString());
        object.put(CATEGORY, category.toJson());
        if (payer != null) {
            object.put(PAYER, payer.toJson());
        }
        if (payee != null) {
            object.put(PAYEE, payee.toJson());
        }
        object.put(BUDGET, budget.toJson());
        object.put(SUM, sum);
        if (comment != null) {
            object.put(COMMENT, comment);
        }

        return object;
    }
}
