package entity.accounts;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "point_roots")
public class PointRoot {
    private int id;
    private int parentId;
    private Date date;
    private int accountId;
    private float amount;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "parent_id")
    public int getParentId() {
        return parentId;
    }
    public void setParentId(int parentId) {
        this.parentId = parentId;
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
    @Column(name = "account")
    public int getAccountId() {
        return accountId;
    }
    public void setAccountId(int account) {
        this.accountId = account;
    }

    @Basic
    @Column(name = "amount")
    public float getAmount() {
        return amount;
    }
    public void setAmount(float amount) {
        this.amount = amount;
    }
}
