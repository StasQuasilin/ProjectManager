package entity.finance.category;

import entity.user.User;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;

import static constants.Keys.*;

@Entity
@Table(name = "categories")
public class Category extends JsonAble {
    private int id;
    private String title;
    private Category parent;
    private boolean hidden;
    private User owner;
    private String currency;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @OneToOne
    @JoinColumn(name = "parent")
    public Category getParent() {
        return parent;
    }
    public void setParent(Category parent) {
        if (parent != null){
            final String currency = parent.getCurrency();
            if (currency != null){
                this.currency = currency;
            }
        }
        this.parent = parent;
    }

    @Basic
    @Column(name = "hidden")
    public boolean isHidden() {
        return hidden;
    }
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    @OneToOne
    @JoinColumn(name = "_owner")
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Basic
    @Column(name = "currency")
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public JSONObject shortJson() {
        JSONObject jsonObject = getJsonObject();
        jsonObject.put(ID, id);
        jsonObject.put(TITLE, title);
        if (currency != null) {
            jsonObject.put(CURRENCY, currency);
        }
        return jsonObject;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = shortJson();
        if (parent != null){
            jsonObject.put(PARENT, parent.toJson());
        }
        jsonObject.put(OWNER, owner.toJson());
        return jsonObject;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return getClass().equals(obj.getClass()) && hashCode() == obj.hashCode();
    }
}
