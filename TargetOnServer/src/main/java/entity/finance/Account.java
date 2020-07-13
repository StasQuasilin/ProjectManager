package entity.finance;

import entity.user.User;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;

import static constants.Keys.*;

@Entity
@Table(name = "accounts")
public class Account extends JsonAble{
    private int id;
    private AccountType type;
    private String title;
    private float sum;
    private String currency;
    private int limit;
    private boolean visible;
    private User owner;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    public AccountType getType() {
        return type;
    }
    public void setType(AccountType type) {
        this.type = type;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
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
    @Column(name = "currency")
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Basic
    @Column(name = "limit")
    public int getLimit() {
        return limit;
    }
    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Basic
    @Column(name = "visible")
    public boolean isVisible() {
        return visible;
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
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
    public JSONObject shortJson() {
        JSONObject jsonObject = getJsonObject();
        jsonObject.put(ID, id);
        jsonObject.put(TITLE, title);
        return jsonObject;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = shortJson();
        jsonObject.put(TYPE, type.toString());
        jsonObject.put(SUM, sum);
        jsonObject.put(CURRENCY, currency);
        jsonObject.put(LIMIT, limit);
        return jsonObject;
    }
}
