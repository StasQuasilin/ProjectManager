package entity.finance.category;

import constants.Keys;
import entity.user.User;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;

@MappedSuperclass
public abstract class AbstractTitle extends JsonAble {
    private int id;
    private String value;
    private User owner;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "_title")
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
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
        final JSONObject object = new JSONObject();
        object.put(Keys.ID, id);
        object.put(Keys.TITLE, value);

        return object;
    }

    @Override
    public String toString() {
        return value;
    }
}
