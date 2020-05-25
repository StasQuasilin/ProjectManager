package entity.transactions;

import constants.Keys;
import constants.TableNames;
import entity.task.Task;
import entity.user.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonAble;

import javax.persistence.*;

import static constants.Keys.*;

/**
 * Created by szpt_user045 on 26.02.2020.
 */
@Entity
@Table(name = TableNames.TRANSACTION_CATEGORY)
public class TransactionCategory extends JsonAble implements Comparable<TransactionCategory> {
    private int id;
    private String name;
    private TransactionCategory parent;
    private User owner;
    private boolean active;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = NAME)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @OneToOne
    @JoinColumn(name = PARENT)
    public TransactionCategory getParent() {
        return parent;
    }
    public void setParent(TransactionCategory parent) {
        this.parent = parent;
    }

    @OneToOne
    @JoinColumn(name = OWNER)
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Basic
    @Column(name = "_active")
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public JSONObject shortJson() {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put(NAME, name);
        return object;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = shortJson();
        object.put(PATH, buildPath());
        return object;
    }

    private JSONArray buildPath() {
        JSONArray path = pool.getArray();
        if (parent != null){
            path.add(parent.shortJson());
            path.addAll(parent.buildPath());
        }
        return path;
    }

    @Override
    public int compareTo(TransactionCategory transactionCategory) {
        return transactionCategory.name.compareTo(name);
    }
}
