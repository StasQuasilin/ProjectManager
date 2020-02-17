package entity.project;

import constants.Keys;
import constants.Tables;
import entity.user.User;
import entity.budget.Budget;
import org.json.simple.JSONObject;
import utils.JsonAble;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by quasilin on 24.02.2019.
 */
@Entity
@Table(name = Tables.PROJECTS)
public class Project implements Keys, JsonAble {
    private int id;
    private User owner;
    private Date beginDate;
    private Date completeDate;
    private Budget budget;
    private String description;
    private Task task;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = OWNER)
    public User getOwner() {
        return owner;
    }
    public void setOwner(User creator) {
        this.owner = creator;
    }

    @Basic
    @Column(name = PROJECT_BEGIN)
    public Date getBeginDate() {
        return beginDate;
    }
    public void setBeginDate(Date date) {
        this.beginDate = date;
    }

    @Basic
    @Column(name = PROJECT_END)
    public Date getCompleteDate() {
        return completeDate;
    }
    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    @Basic
    @Column(name = DESCRIPTION)
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @OneToOne
    @JoinColumn(name = BUDGET)
    public Budget getBudget() {
        return budget;
    }
    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    @OneToOne
    @JoinColumn(name = TASK)
    public Task getTask() {
        return task;
    }
    public void setTask(Task task) {
        this.task = task;
    }

    @Transient
    public float getTimeProgress() {
        long b = beginDate.getTime();
        long n = Date.valueOf(LocalDate.now()).getTime();
        long c = completeDate.getTime();
        return 1f * (n - b) / (c - n);

    }

    @Override
    public JSONObject toJson() {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put(TITLE, task.getTitle());
        object.put(BEGIN, beginDate.toString());
        object.put(END, completeDate.toString());
        if (budget != null) {
            object.put(BUDGET, budget.toJson());
        }
        object.put(DESCRIPTION, description);
        return object;
    }
}
