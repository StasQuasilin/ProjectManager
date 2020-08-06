package entity.task;

import entity.finance.category.Category;
import entity.user.User;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;

import java.sql.Date;

import static constants.Keys.*;

@Entity
@Table(name = "tasks")
public class Task extends JsonAble {
    private int id;
    private String uid;
    private Date deadline;
    private Category category;
    private TaskStatus status;
    private User doer;
    private String result;
    private TaskPriority priority;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "uid")
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "deadline")
    public Date getDeadline() {
        return deadline;
    }
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @OneToOne
    @JoinColumn(name = "_category")
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    @Basic
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    public TaskStatus getStatus() {
        return status;
    }
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @OneToOne
    @JoinColumn(name = "doer")
    public User getDoer() {
        return doer;
    }
    public void setDoer(User doer) {
        this.doer = doer;
    }

    @Basic
    @Column(name = "result")
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "_priority")
    public TaskPriority getPriority() {
        return priority;
    }
    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    @Override
    public JSONObject shortJson() {
        JSONObject jsonObject = getJsonObject();
        jsonObject.put(ID, id);
        jsonObject.put(CATEGORY, category.getId());
        jsonObject.put(TITLE, category.getTitle());
        jsonObject.put(STATUS, status.toString());
        return jsonObject;
    }

    @Override
    public JSONObject toJson() {
        final JSONObject jsonObject = shortJson();
        jsonObject.put(PARENT, category.getParent().toJson());
        jsonObject.put(UID, uid);

        if (doer != null){
            jsonObject.put(DOER, doer.toJson());
        }
        if (result != null){
            jsonObject.put(RESULT, result);
        }
        return jsonObject;
    }

    @Transient
    public User getOwner() {
        return category.getOwner();
    }

    @Transient
    public String getTitle() {
        return category.getTitle();
    }
}
