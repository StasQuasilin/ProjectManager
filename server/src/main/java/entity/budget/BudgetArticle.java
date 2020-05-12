package entity.budget;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 27.05.2019.
 */
@Entity
@Table(name = "budget_articles")
public class BudgetArticle {
    private long id;
    private BudgetPoint point;
    private Timestamp time;
    private float quantity;
    private String description;

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "point")
    public BudgetPoint getPoint() {
        return point;
    }
    public void setPoint(BudgetPoint point) {
        this.point = point;
    }

    @Basic
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }
    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Basic
    @Column(name = "quantity")
    public float getQuantity() {
        return quantity;
    }
    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
