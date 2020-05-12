package entity.budget;

import constants.Keys;
import constants.Tables;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by szpt_user045 on 27.05.2019.
 */
@Entity
@Table(name = Tables.BUDGET_POINTS)
public class BudgetPoint implements Keys {
    private long id;
    private Budget budget;
    private PointScale scale;
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
    @JoinColumn(name = BUDGET)
    public Budget getBudget() {
        return budget;
    }
    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = SCALE)
    public PointScale getScale() {
        return scale;
    }
    public void setScale(PointScale scale) {
        this.scale = scale;
    }

    @Basic
    @Column(name = DATE)
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = QUANTITY)
    public float getQuantity() {
        return quantity;
    }
    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }
}
