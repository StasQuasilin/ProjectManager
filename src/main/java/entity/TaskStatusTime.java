package entity;

import entity.project.TaskStatus;
import entity.user.User;
import org.json.simple.JSONObject;
import utils.JsonAble;

import javax.persistence.*;
import java.sql.Timestamp;

import static constants.Keys.*;

@Entity
@Table(name = "task_status_time")
public class TaskStatusTime extends JsonAble {

    private int id;
    private TaskStatus status;
    private Timestamp timestamp;
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    public TaskStatus getStatus() {
        return status;
    }
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Basic
    @Column(name = "time")
    public Timestamp getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @OneToOne
    @JoinColumn(name = "user")
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put(STATUS, status.toString());
        object.put(TIME, timestamp.toString());
        object.put(USER, user.shortJson());
        return object;
    }
}
