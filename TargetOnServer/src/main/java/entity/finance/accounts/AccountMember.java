package entity.finance.accounts;

import entity.user.User;

import javax.persistence.*;

@Entity
@Table(name = "account_members")
public class AccountMember {
    private int id;
    private Account account;
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "account")
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }

    @OneToOne
    @JoinColumn(name = "_user")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
