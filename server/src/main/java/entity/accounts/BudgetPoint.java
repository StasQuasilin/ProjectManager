package entity.accounts;

import constants.Keys;
import constants.TableNames;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by szpt_user045 on 27.05.2019.
 */
@Entity
@Table(name = TableNames.BUDGET_POINTS)
public class BudgetPoint implements Keys {
    private long id;
    private Account account;
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
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
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
