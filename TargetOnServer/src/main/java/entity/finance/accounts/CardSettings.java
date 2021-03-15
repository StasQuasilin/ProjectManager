package entity.finance.accounts;

import constants.Keys;
import org.json.simple.JSONObject;
import utils.json.JsonAble;
import utils.json.JsonObject;

import javax.persistence.*;

@Entity
@Table(name = "card_settings")
public class CardSettings extends JsonAble {
    private int id;
    private Account account;
    private long exemption;
    private boolean remember;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "_account")
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }

    @Basic
    @Column(name = "_exemption")
    public long getExemption() {
        return exemption;
    }
    public void setExemption(long exemption) {
        this.exemption = exemption;
    }

    @Basic
    @Column(name = "_remember")
    public boolean isRemember() {
        return remember;
    }
    public void setRemember(boolean remember) {
        this.remember = remember;
    }

    @Override
    public JSONObject toJson() {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put(Keys.EXEMPTION, exemption);
        jsonObject.put(Keys.REMEMBER, remember);
        return jsonObject;
    }
}
