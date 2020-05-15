package entity.accounts;

import constants.TableNames;
import entity.user.User;
import org.json.simple.JSONObject;
import utils.JsonAble;

import javax.persistence.*;
import static constants.Keys.*;

@Entity
@Table(name = TableNames.USER_CURRENCY)
public class UserCurrency extends JsonAble {
    private int id;
    private User user;
    private Currency currency;
    private boolean main;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = USER)
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @OneToOne
    @JoinColumn(name = CURRENCY)
    public Currency getCurrency() {
        return currency;
    }
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Basic
    @Column(name = MAIN)
    public boolean isMain() {
        return main;
    }
    public void setMain(boolean main) {
        this.main = main;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put(CURRENCY, currency.toJson());
        object.put(MAIN, main);
        return object;
    }
}
