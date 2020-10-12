package entity.finance.accounts;

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
    private User owner;
    private boolean show;

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
    @Column(name = "_type")
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
    @Column(name = "_limit")
    public int getLimit() {
        return limit;
    }
    public void setLimit(int limit) {
        this.limit = limit;
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
    @Column(name = "_show")
    public boolean isShow() {
        return show;
    }
    public void setShow(boolean showInPayments) {
        this.show = showInPayments;
    }

    @Override
    public JSONObject shortJson() {
        JSONObject jsonObject = getJsonObject();
        jsonObject.put(ID, id);
        jsonObject.put(TITLE, title);
        jsonObject.put(SUM, sum);
        jsonObject.put(CURRENCY, currency);
        return jsonObject;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = shortJson();
        jsonObject.put(TYPE, type.toString());
        jsonObject.put(LIMIT, limit);
        jsonObject.put(SHOW, show);
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
