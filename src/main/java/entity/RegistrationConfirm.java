package entity;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by quasilin on 23.02.2019.
 */
@Entity
@Table(name = "registration_confirm")
public class RegistrationConfirm {
    private int id;
    private String token;
    private String email;
    private String password;

    public RegistrationConfirm() {
        token = UUID.randomUUID().toString();
    }

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "token")
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
