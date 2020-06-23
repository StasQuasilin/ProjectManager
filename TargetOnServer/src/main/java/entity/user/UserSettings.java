package entity.user;

import entity.finance.Currency;

import javax.persistence.*;

@Entity
@Table(name = "user_settings")
public class UserSettings {
    private int id;
    private User user;
    private String mainCurrency;
    private String locale;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "user")
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @Basic
    @Column(name = "currency")
    public String getMainCurrency() {
        return mainCurrency;
    }
    public void setMainCurrency(String mainCurrency) {
        this.mainCurrency = mainCurrency;
    }

    @Basic
    @Column(name = "locale")
    public String getLocale() {
        return locale;
    }
    public void setLocale(String locale) {
        this.locale = locale;
    }
}
