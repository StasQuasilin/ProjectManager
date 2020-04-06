package entity.project;

import constants.Keys;
import entity.budget.Budget;
import entity.user.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonAble;

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
    private Task parent;
    private String title;
    private float cost;
    private Budget budget;
    private User owner;
    private User doer;
    private String description;
    private int children;


    public Task() {}

    public Task(String title) {
        status = TaskStatus.active;
        this.title = title;
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
    @JoinColumn(name = PARENT)
    public Task getParent() {
        return parent;
    }
    public void setParent(Task parent) {
        this.parent = parent;
        super.setParent(parent);
    }

    @Basic
    @Column(name = TITLE)
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
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
    public Budget getBudget() {
        return budget;
    }
    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    @Override
    public int compareTo(Task o) {
        return o.getTitle().compareTo(title);
    }

    @Override
    public JSONObject shortJson() {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put(TITLE, title);
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

        if (parent != null) {
            object.put(PARENT, parent.getId());
        }
        object.put(PATH, buildPath());
        object.put(DESCRIPTION, description);
        object.put(IS_GROUP, children > 0);
        object.put(CHILDREN, children);
        if (doer != null){
            object.put(DOER, doer.toJson());
        }
        object.put(COAST, cost);
        return object;
    }

    @Transient
    public boolean isGroup() {
        return children > 0;
    }

    @Transient
    public boolean isTop(){
        return parent == null;
    }
}
