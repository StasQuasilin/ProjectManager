package entity.user;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_access")
public class UserAccess implements Serializable {
    private User user;
    private String login;
    private int passwordHash;
    private String token;

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
    @Column(name = "_login")
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "_password_hash")
    public int getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(int passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Basic
    @Column(name = "_token")
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
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
