package entity.budget;

import entity.User;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 25.02.2019.
 */
@Entity
@Table(name = "budgets")
public class Budget {
    private int id;
    private String title;
    private BudgetSize budgetSize;
    private BudgetType budgetType;
    private float budgetSum;
    private String currency;
    private User owner;
    private Timestamp create;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "size")
    public BudgetSize getBudgetSize() {
        return budgetSize;
    }
    public void setBudgetSize(BudgetSize budgetSize) {
        this.budgetSize = budgetSize;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    public BudgetType getBudgetType() {
        return budgetType;
    }
    public void setBudgetType(BudgetType budgetType) {
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

    @Basic
    @Column(name = "created")
    public Timestamp getCreate() {
        return create;
    }

    public void setCreate(Timestamp create) {
        this.create = create;
    }
}
