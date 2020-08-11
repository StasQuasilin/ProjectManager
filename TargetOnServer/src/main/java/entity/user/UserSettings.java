package entity.user;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_settings")
public class UserSettings implements Serializable {
    private User user;
    private String currency;
    private String locale;
    private String avatar;

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

    @Transient
    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public int hashCode() {
        return user.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass().equals(getClass()) && hashCode() == obj.hashCode();
    }
}
