package entity.task;

import constants.Keys;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;

@Entity
@Table(name = "task_dependency")
public class TaskDependency extends JsonAble {
    private int id;
    private Task dependent;
    private Task principal;
    private TaskStatus principalStatus;

    @Id
    @Column(name = "_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "_dependent")
    public Task getDependent() {
        return dependent;
    }
    public void setDependent(Task dependent) {
        this.dependent = dependent;
    }

    @ManyToOne
    @JoinColumn(name = "_principal")
    public Task getPrincipal() {
        return principal;
    }
    public void setPrincipal(Task principal) {
        this.principal = principal;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "_principal_status")
    public TaskStatus getPrincipalStatus() {
        return principalStatus;
    }
    public void setPrincipalStatus(TaskStatus principalStatus) {
        this.principalStatus = principalStatus;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put(Keys.ID, id);
        json.put(Keys.DEPENDENT, dependent.toJson());
        json.put(Keys.PRINCIPAL, principal.toJson());
        json.put(Keys.STATUS, principalStatus.toString());
        return json;
    }
}
