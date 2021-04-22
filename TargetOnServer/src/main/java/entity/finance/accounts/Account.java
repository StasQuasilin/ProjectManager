package entity.finance.accounts;

import entity.finance.category.Header;
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
    private Header header;
    private float sum;
    private String currency;
    private int limit;
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

    @OneToOne
    @JoinColumn(name = "_title")
    public Header getHeader() {
        return header;
    }
    public void setHeader(Header title) {
        this.header = title;
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
        jsonObject.put(TITLE, header.getValue());
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

    @Transient
    public User getOwner() {
        return header.getOwner();
    }
}
