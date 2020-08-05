package entity.user;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_access")
public class UserAccess implements Serializable {
    private User user;
    private String login;
    private String password;

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
    @Column(name = "_password")
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
