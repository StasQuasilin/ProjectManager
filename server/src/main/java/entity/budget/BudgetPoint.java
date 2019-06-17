package entity.budget;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by szpt_user045 on 27.05.2019.
 */
@Entity
@Table(name = "budget_points")
public class BudgetPoint {
    private long id;
    private Budget budget;
    private Date date;
    private float quantity;

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "budget")
    public Budget getBudget() {
        return budget;
    }
    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    @Basic
    @Column(name = "date")
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "quantity")
    public float getQuantity() {
        return quantity;
    }
    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }
}
