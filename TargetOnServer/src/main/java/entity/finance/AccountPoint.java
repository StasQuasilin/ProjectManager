package entity.finance;

import javax.persistence.*;

@Entity
@Table(name = "account_points")
public class AccountPoint {
    private int id;
    private PointScale scale;
    private int account;
    private float amount;
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
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "scale")
    public PointScale getScale() {
        return scale;
    }
    public void setScale(PointScale scale) {
        this.scale = scale;
    }

    @Basic
    @Column(name = "account")
    public int getAccount() {
        return account;
    }
    public void setAccount(int account) {
        this.account = account;
    }

    @Basic
    @Column(name = "amount")
    public float getAmount() {
        return amount;
    }
    public void setAmount(float amount) {
        this.amount = amount;
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
