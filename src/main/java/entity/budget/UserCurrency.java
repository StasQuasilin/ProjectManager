package entity.budget;

import constants.Tables;
import entity.user.User;

import javax.persistence.*;
import static constants.Keys.*;

@Entity
@Table(name = Tables.USER_CURRENCY)
public class UserCurrency {
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
}
