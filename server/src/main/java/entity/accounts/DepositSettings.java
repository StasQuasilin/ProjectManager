package entity.accounts;

import org.json.simple.JSONObject;
import utils.JsonAble;

import javax.persistence.*;
import java.sql.Date;

import static constants.Keys.*;

@Entity
@Table(name = "deposit_settings")
public class DepositSettings extends JsonAble {
    private int id;
    private Account account;
    private Date open;
    private Date close;
    private float bid;
    private int paymentPeriod;
    private Account paymentAccount;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "account")
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }

    @Basic
    @Column(name = "_open")
    public Date getOpen() {
        return open;
    }
    public void setOpen(Date open) {
        this.open = open;
    }

    @Basic
    @Column(name = "_close")
    public Date getClose() {
        return close;
    }
    public void setClose(Date close) {
        this.close = close;
    }

    @Basic
    @Column(name = "bid")
    public float getBid() {
        return bid;
    }
    public void setBid(float bid) {
        this.bid = bid;
    }

    @Basic
    @Column(name = "payment_period")
    public int getPaymentPeriod() {
        return paymentPeriod;
    }
    public void setPaymentPeriod(int paymentPeriod) {
        this.paymentPeriod = paymentPeriod;
    }

    @OneToOne
    @JoinColumn(name = "payment_account")
    public Account getPaymentAccount() {
        return paymentAccount;
    }
    public void setPaymentAccount(Account paymentAccount) {
        this.paymentAccount = paymentAccount;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = pool.getObject();
        object.put(OPEN, open.toString());
        object.put(CLOSE, close.toString());
        object.put(BID, bid);
        object.put(PAYMENT, paymentPeriod);
        object.put(ACCOUNT, paymentAccount.getId());
        return object;
    }
}
