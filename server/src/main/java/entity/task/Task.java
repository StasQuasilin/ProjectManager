package entity.task;

import constants.Keys;
import entity.accounts.Account;
import entity.project.iTask;
import entity.transactions.TransactionCategory;
import entity.user.User;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 25.02.2019.
 */
@Entity
@Table(name = "tasks")
public class Task extends iTask implements Comparable<Task>, Keys {
    private int id;
    private Date date;
    private Timestamp timestamp;
    private TaskStatus status;
    private TaskType type;
    private TransactionCategory category;
    private float cost;
    private Account account;
    private User owner;
    private User doer;
    private String description;
    private int children;
    private TaskStatistic statistic;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "task", cascade = CascadeType.ALL)
    public TaskStatistic getStatistic() {
        return statistic;
    }
    public void setStatistic(TaskStatistic statistic) {
        this.statistic = statistic;
    }

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
    @Column(name = TIMESTAMP)
    public Timestamp getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Timestamp date) {
        this.timestamp = date;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = STATUS)
    public TaskStatus getStatus() {
        return status;
    }
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = TYPE)
    public TaskType getType() {
        return type;
    }
    public void setType(TaskType type) {
        this.type = type;
    }

    @OneToOne
    @JoinColumn(name = "category")
    public TransactionCategory getCategory() {
        return category;
    }
    public void setCategory(TransactionCategory category) {
        this.category = category;
    }

    @Basic
    @Column(name = COST)
    public float getCost() {
        return cost;
    }
    public void setCost(float cost) {
        this.cost = cost;
    }

    @OneToOne
    @JoinColumn(name = OWNER)
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }

    @OneToOne
    @JoinColumn(name = DOER)
    public User getDoer() {
        return doer;
    }
    public void setDoer(User doer) {
        this.doer = doer;
    }

    @Basic
    @Column(name = DESCRIPTION)
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = CHILDREN)
    public int getChildren() {
        return children;
    }
    public void setChildren(int children) {
        this.children = children;
    }

    @OneToOne
    @JoinColumn(name = BUDGET)
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public int compareTo(Task o) {
        return o.getCategory().compareTo(getCategory());
    }

    @Override
    public JSONObject shortJson() {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put(TITLE, category.getName());
        return object;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = shortJson();

        if (date != null){
            object.put(DATE, date.toString());
        }
        if (timestamp != null) {
            object.put(TIMESTAMP, timestamp.toString());
        }
        object.put(STATUS, status.toString());

        object.put(DESCRIPTION, description);
        object.put(IS_GROUP, children > 0);
        object.put(CHILDREN, children);
        if (doer != null){
            object.put(DOER, doer.toJson());
        }
        object.put(COAST, cost);
        if (statistic != null){
            object.put(STATISTIC, statistic.toJson());
        }
        return object;
    }

    @Transient
    public boolean isGroup() {
        return children > 0;
    }

    @Transient
    public boolean isTop(){
        return category.getParent() == null;
    }

    @Transient
    public int getChildrenCount(TaskStatus status) {
        if (statistic != null) {
            switch (status) {
                case active:
                    return statistic.getActiveChildren();
                case progressing:
                    return statistic.getProgressingChildren();
                case done:
                    return statistic.getDoneChildren();
            }
        }
        return 0;
    }
}
