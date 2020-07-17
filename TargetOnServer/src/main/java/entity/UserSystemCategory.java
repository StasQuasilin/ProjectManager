package entity;

import entity.finance.category.Category;
import entity.user.User;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_system_category")
public class UserSystemCategory implements Serializable {
    private User owner;
    private Category createAccount;
    private Category accountCorrection;

    @Id
    @OneToOne
    @JoinColumn(name = "_owner")
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }

    @OneToOne
    @JoinColumn(name = "account_create")
    public Category getCreateAccount() {
        return createAccount;
    }
    public void setCreateAccount(Category createAccount) {
        this.createAccount = createAccount;
    }

    @OneToOne
    @JoinColumn(name = "account_correction")
    public Category getAccountCorrection() {
        return accountCorrection;
    }
    public void setAccountCorrection(Category accountCorrection) {
        this.accountCorrection = accountCorrection;
    }
}
