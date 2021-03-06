package entity.task;

import entity.finance.category.Header;
import entity.user.User;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;

import java.sql.Date;
import java.util.Set;

import static constants.Keys.*;

@Entity
@Table(name = "tasks")
public class Task extends JsonAble {
    private int id;
    private String uid;
    private Header header;
    private TaskStatus status;
    private Set<TaskDependency> dependencies;
    private Set<TaskDependency> principals;
    private Date date;
    private Date deadline;
    private User doer;
    private String result;
    private TaskPriority priority;
    private boolean doneIfChildren;

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

    @OneToMany(fetch = FetchType.EAGER, mappedBy="dependent", cascade = CascadeType.ALL)
    public Set<TaskDependency> getDependencies() {
        return dependencies;
    }
    public void setDependencies(Set<TaskDependency> dependencies) {
        this.dependencies = dependencies;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "principal", cascade = CascadeType.ALL)
    public Set<TaskDependency> getPrincipals() {
        return principals;
    }
    public void setPrincipals(Set<TaskDependency> principals) {
        this.principals = principals;
    }

    @Basic
    @Column(name = "_date")
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
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
    @JoinColumn(name = "_header")
    public Header getHeader() {
        return header;
    }
    public void setHeader(Header header) {
        this.header = header;
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

    @Basic
    @Column(name = "_done_if_children")
    public boolean isDoneIfChildren() {
        return doneIfChildren;
    }
    public void setDoneIfChildren(boolean doneIfChildren) {
        this.doneIfChildren = doneIfChildren;
    }

    @Override
    public JSONObject shortJson() {
        JSONObject jsonObject = getJsonObject();
        jsonObject.put(ID, id);
        jsonObject.put(HEADER, header.getId());
        jsonObject.put(TITLE, header.getTitle());
        jsonObject.put(STATUS, status.toString());
        return jsonObject;
    }

    @Override
    public JSONObject toJson() {
        final JSONObject jsonObject = shortJson();
        if (header != null && header.getParent() != null){
            jsonObject.put(PARENT, header.getParent().toJson());
        }

        jsonObject.put(UID, uid);
        if (date != null){
            jsonObject.put(DATE, date.toString());
        }
        if (deadline != null){
            jsonObject.put(DEADLINE, deadline.toString());
        }
        if (doer != null){
            jsonObject.put(DOER, doer.toJson());
        }
        if (result != null){
            jsonObject.put(RESULT, result);
        }
        jsonObject.put(DONE_IF, doneIfChildren);
        return jsonObject;
    }

    @Transient
    public User getOwner() {
        return header.getOwner();
    }

    @Transient
    public String getTitle() {
        return header.getTitle();
    }
}
