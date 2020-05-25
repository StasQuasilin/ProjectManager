package entity.task;

import constants.Keys;
import entity.accounts.Account;
import entity.project.iTask;
import entity.transactions.TransactionCategory;
import entity.user.User;
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
public class Task extends JsonAble implements Comparable<Task>, Keys {
    private int id;
    private TransactionCategory category;
    private TaskStatus status;
    private TaskType type;
    private User owner;
    private User doer;
    private String description;
    private TaskStatistic statistic;

    public Task() {}

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

    @Enumerated(EnumType.STRING)
    @Column(name = STATUS)
    public TaskStatus getStatus() {
        return status;
    }
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
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

    @Override
    public int compareTo(Task o) {
        return o.getCategory().compareTo(getCategory());
    }

    @Override
    public JSONObject shortJson() {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        if (category != null) {
            object.put(TITLE, category.getName());
        }
        return object;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = shortJson();
        object.put(STATUS, status.toString());
        object.put(DESCRIPTION, description);
        if (doer != null){
            object.put(DOER, doer.toJson());
        }
        if (statistic != null){
            object.put(STATISTIC, statistic.toJson());
        }
        return object;
    }

//    @Transient
//    public boolean isGroup() {
//        return children > 0;
//    }

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

    public void setParent(Task parent) {

    }

    @Transient
    public TransactionCategory getParent() {
        return category.getParent();
    }

    public static Task newTask(String title){
        Task task = new Task();
        TransactionCategory category = new TransactionCategory();
        if (title != null) {
            category.setName(title);
        }
        task.setCategory(category);
        return task;
    }

    public void setName(String title) {
        category.setName(title);
    }

    @Transient
    public String getTitle() {
        return category.getName();
    }
}
