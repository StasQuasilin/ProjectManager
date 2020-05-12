package entity.user;

import constants.Keys;
import constants.TableNames;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 22.02.2019.
 */
@Entity
@Table(name = TableNames.USER_ASSES)
public class UserAccess implements Keys{
    private int id;
    private String email;
    private String password;
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = EMAIL)
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = PASSWORD)
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @OneToOne
    @JoinColumn(name = USER)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
