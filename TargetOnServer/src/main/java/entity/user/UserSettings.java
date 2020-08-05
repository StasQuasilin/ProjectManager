package entity.user;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_settings")
public class UserSettings implements Serializable {
    private User user;
    private String currency;
    private String locale;

    @Id
    @OneToOne
    @JoinColumn(name = "_user")
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @Basic
    @Column(name = "_currency")
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String mainCurrency) {
        this.currency = mainCurrency;
    }

    @Basic
    @Column(name = "_locale")
    public String getLocale() {
        return locale;
    }
    public void setLocale(String locale) {
        this.locale = locale;
    }
}
