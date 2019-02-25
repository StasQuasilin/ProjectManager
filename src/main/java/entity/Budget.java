package entity;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 25.02.2019.
 */
@Entity
@Table(name = "budgets")
public class Budget {
    private int id;
    private String title;
    private String budgetType;
    private float budgetSum;
    private String currency;
    private User owner;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "type")
    public String getBudgetType() {
        return budgetType;
    }
    public void setBudgetType(String budgetType) {
        this.budgetType = budgetType;
    }

    @Basic
    @Column(name = "sum")
    public float getBudgetSum() {
        return budgetSum;
    }
    public void setBudgetSum(float budgetSum) {
        this.budgetSum = budgetSum;
    }

    @Basic
    @Column(name = "currency")
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @OneToOne
    @JoinColumn(name = "owner")
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }
}
