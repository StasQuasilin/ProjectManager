package entity.transactions.fast.transaction;

import entity.accounts.Counterparty;
import entity.accounts.Currency;
import entity.transactions.Transaction;
import entity.transactions.TransactionCategory;
import entity.user.User;
import org.json.simple.JSONObject;
import utils.JsonAble;

import javax.persistence.*;
import static constants.Keys.*;

@Entity
@Table(name = "fast_transactions")
public class FastTransaction extends JsonAble {
    private int id;
    private Transaction transaction;
    private int counter;
    private User owner;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "transaction")
    public Transaction getTransaction() {
        return transaction;
    }
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @Basic
    @Column(name = "counter")
    public int getCounter() {
        return counter;
    }
    public void setCounter(int counter) {
        this.counter = counter;
    }

    @OneToOne
    @JoinColumn(name = "owner")
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = transaction.toJson();
        object.put(ID, id);
        object.put(COUNTER, counter);
        return object;
    }

    public void increment() {
        counter++;
    }
}
