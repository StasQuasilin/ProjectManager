package entity;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by quasilin on 24.02.2019.
 */
@Entity
@Table(name = "projects")
public class Project {
    private int id;
    private User owner;
    private Date beginDate;
    private Date completeDate;
    private String title;
    private Budget budget;
    private String description;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "owner")
    public User getOwner() {
        return owner;
    }
    public void setOwner(User creator) {
        this.owner = creator;
    }

    @Basic
    @Column(name = "begin_date")
    public Date getBeginDate() {
        return beginDate;
    }
    public void setBeginDate(Date date) {
        this.beginDate = date;
    }

    @Basic
    @Column(name = "complete_date")
    public Date getCompleteDate() {
        return completeDate;
    }
    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
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
    @Column(name = "description")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @OneToOne
    @JoinColumn(name = "budget")
    public Budget getBudget() {
        return budget;
    }
    public void setBudget(Budget budget) {
        this.budget = budget;
    }
}
