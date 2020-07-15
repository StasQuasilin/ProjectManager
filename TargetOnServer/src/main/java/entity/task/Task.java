package entity.task;

import entity.finance.Category;
import entity.user.User;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;

import static constants.Keys.*;

@Entity
@Table(name = "tasks")
public class Task extends JsonAble {
    private int id;
    private String uid;
    private Category category;
    private TaskStatus status;
    private User doer;
    private String result;

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

    @OneToOne
    @JoinColumn(name = "category")
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

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = getJsonObject();
        jsonObject.put(ID, id);
        jsonObject.put(CATEGORY, category.getId());
        jsonObject.put(PARENT, category.getParent().toJson());
        jsonObject.put(TITLE, category.getTitle());
        jsonObject.put(UID, uid);
        jsonObject.put(STATUS, status.toString());
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
