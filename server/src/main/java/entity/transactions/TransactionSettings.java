package entity.transactions;

import entity.budget.Account;
import entity.budget.Currency;
import entity.user.User;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by szpt_user045 on 27.02.2020.
 */
@Entity
@Table(name = "transactions_settings")
public class TransactionSettings {
    private int id;
    private Date beginDate;
    private Date finalDate;
    private TransactionRepeat repeat;
    private User owner;
    private Account account;
    private TransactionCategory category;
    private float amount;
    private Currency currency;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "begin_date")
    public Date getBeginDate() {
        return beginDate;
    }
    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    @Basic
    @Column(name = "final_date")
    public Date getFinalDate() {
        return finalDate;
    }
    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "repeat")
    public TransactionRepeat getRepeat() {
        return repeat;
    }
    public void setRepeat(TransactionRepeat repeat) {
        this.repeat = repeat;
    }

    @OneToOne
    @JoinColumn(name = "owner")
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }

    @OneToOne
    @JoinColumn(name = "budget")
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }

    @OneToOne
    @JoinColumn(name = "category")
    public TransactionCategory getCategory() {
        return category;
    }
    public void setCategory(TransactionCategory category) {
        this.category = category;
    }

    @Basic
    @Column(name = "amount")
    public float getAmount() {
        return amount;
    }
    public void setAmount(float amount) {
        this.amount = amount;
    }

    @OneToOne
    @JoinColumn(name = "currency")
    public Currency getCurrency() {
        return currency;
    }
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
