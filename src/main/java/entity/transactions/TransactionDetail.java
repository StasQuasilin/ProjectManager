package entity.transactions;

import entity.budget.Budget;
import entity.budget.Currency;
import org.json.simple.JSONObject;
import utils.JsonAble;

import javax.persistence.*;

import static constants.Keys.*;

@Entity
@Table(name = "transaction_details")
public class TransactionDetail extends JsonAble {
    private int id;
    private Transaction transaction;
    private TransactionCategory category;
    private Budget account;
    private float sum;
    private float rate = 1;
    private Currency currency;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "_transaction")
    public Transaction getTransaction() {
        return transaction;
    }
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @OneToOne
    @JoinColumn(name = "_category")
    public TransactionCategory getCategory() {
        return category;
    }
    public void setCategory(TransactionCategory title) {
        this.category = title;
    }

    @OneToOne
    @JoinColumn(name = "account")
    public Budget getAccount() {
        return account;
    }
    public void setAccount(Budget account) {
        this.account = account;
    }

    @Basic
    @Column(name = "sum")
    public float getSum() {
        return sum;
    }
    public void setSum(float sum) {
        this.sum = sum;
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
    @JoinColumn(name = "currency")
    public Currency getCurrency() {
        return currency;
    }
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put(CATEGORY, category.toJson());
        object.put(ACCOUNT, account.toJson());
        object.put(SUM, sum);
        object.put(RATE, rate);
        object.put(CURRENCY, currency.toJson());
        return object;
    }
}
