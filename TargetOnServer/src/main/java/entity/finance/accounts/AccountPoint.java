package entity.finance.accounts;

import entity.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "account_points")
public class AccountPoint {
    private int id;
    private Date date;
    private PointScale scale;
    private int account;
    private PointType type;
    private User owner;
    private float plus;
    private float minus;
    private float rate;
    private String currency;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "_date")
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "_scale")
    public PointScale getScale() {
        return scale;
    }
    public void setScale(PointScale scale) {
        this.scale = scale;
    }

    @Basic
    @Column(name = "_account")
    public int getAccount() {
        return account;
    }
    public void setAccount(int account) {
        this.account = account;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "_type")
    public PointType getType() {
        return type;
    }
    public void setType(PointType scaleType) {
        this.type = scaleType;
    }

    @OneToOne
    @JoinColumn(name = "_owner")
    public User getOwner() {
        return owner;
    }
    public void setOwner(User scaleOwner) {
        this.owner = scaleOwner;
    }

    @Basic
    @Column(name = "_plus")
    public float getPlus() {
        return plus;
    }
    public void setPlus(float amount) {
        this.plus = amount;
    }

    @Basic
    @Column(name = "_minus")
    public float getMinus() {
        return minus;
    }
    public void setMinus(float minus) {
        this.minus = minus;
    }

    @Basic
    @Column(name = "rate")
    public float getRate() {
        return rate;
    }
    public void setRate(float rate) {
        this.rate = rate;
    }

    @Basic
    @Column(name = "currency")
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
