package entity.user;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "demo_accounts")
public class DemoAccount {
    private int id;
    private User user;
    private String ip;
    private Date create;

    @Id
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

    @Basic
    @Column(name = "_ip")
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }

    @Basic
    @Column(name = "_create")
    public Date getCreate() {
        return create;
    }
    public void setCreate(Date create) {
        this.create = create;
    }
}

