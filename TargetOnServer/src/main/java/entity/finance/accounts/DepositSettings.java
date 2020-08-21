package entity.finance.accounts;

import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

import static constants.Keys.*;

@Entity
@Table(name = "deposit_settings")
public class DepositSettings extends JsonAble implements Serializable {
    private Account account;
    private Date open;
    private long period;
    private float rate;
    private boolean capitalization;

    @Id
    @OneToOne
    @JoinColumn(name = "_account")
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
    @Column(name = "_period")
    public long getPeriod() {
        return period;
    }
    public void setPeriod(long period) {
        this.period = period;
    }

    @Basic
    @Column(name = "_rate")
    public float getRate() {
        return rate;
    }
    public void setRate(float rate) {
        this.rate = rate;
    }

    @Basic
    @Column(name = "_capitalization")
    public boolean isCapitalization() {
        return capitalization;
    }
    public void setCapitalization(boolean capitalization) {
        this.capitalization = capitalization;
    }

    @Override
    public int hashCode() {
        return account.getId();
    }

    @Override
    public boolean equals(Object obj) {
        return getClass().equals(obj.getClass()) && hashCode() == obj.hashCode();
    }

    @Override
    public JSONObject toJson() {
        final JSONObject jsonObject = getJsonObject();
        jsonObject.put(OPEN, open.toString());
        jsonObject.put(PERIOD, period);
        jsonObject.put(RATE, rate);
        jsonObject.put(CAPITALIZATION, capitalization);
        return jsonObject;
    }
}
