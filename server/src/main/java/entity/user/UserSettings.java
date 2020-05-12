package entity.user;

import entity.transactions.TransactionCategory;

import javax.persistence.*;

@Entity
@Table(name = "user_settings")
public class UserSettings {
    private int id;
    private User user;
    private TransactionCategory correctionCategory;
    private TransactionCategory openCategory;

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

    @OneToOne
    @JoinColumn(name = "correction_category")
    public TransactionCategory getCorrectionCategory() {
        return correctionCategory;
    }
    public void setCorrectionCategory(TransactionCategory correctionCategory) {
        this.correctionCategory = correctionCategory;
    }

    @OneToOne
    @JoinColumn(name = "open_category")
    public TransactionCategory getOpenCategory() {
        return openCategory;
    }
    public void setOpenCategory(TransactionCategory openCategory) {
        this.openCategory = openCategory;
    }
}
