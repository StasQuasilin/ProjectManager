package entity.finance;

import entity.user.User;

import javax.persistence.*;

@Entity
@Table(name = "user_currency")
public class UserCurrency {
    private int id;
    private User user;
    private Currency currency;

    @Id
    @Column(name = "_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "_user")
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @OneToOne
    @JoinColumn(name = "_currency")
    public Currency getCurrency() {
        return currency;
    }
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
