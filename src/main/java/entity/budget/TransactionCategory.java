package entity.budget;

import constants.Keys;
import constants.Tables;
import entity.user.User;
import org.json.simple.JSONObject;
import utils.JsonAble;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 26.02.2020.
 */
@Entity
@Table(name = Tables.TRANSACTION_CATEGORY)
public class TransactionCategory implements Keys, JsonAble {
    private int id;
    private String name;
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
    @Column(name = NAME)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
        object.put(NAME, name);
        return object;
    }
}
