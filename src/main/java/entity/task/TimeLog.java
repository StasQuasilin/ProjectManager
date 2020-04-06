package entity.task;

import constants.Keys;
import entity.project.Task;
import entity.user.User;
import org.json.simple.JSONObject;
import utils.JsonAble;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "task_time_log")
public class TimeLog extends JsonAble implements Keys {
    private int id;
    private Task task;
    private Timestamp begin;
    private Timestamp end;
    private User doer;
    private TimeLogType logType;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "task")
    public Task getTask() {
        return task;
    }
    public void setTask(Task task) {
        this.task = task;
    }

    @Basic
    @Column(name = "begin_time")
    public Timestamp getBegin() {
        return begin;
    }
    public void setBegin(Timestamp begin) {
        this.begin = begin;
    }

    @Basic
    @Column(name = "end_time")
    public Timestamp getEnd() {
        return end;
    }
    public void setEnd(Timestamp end) {
        this.end = end;
    }

    @OneToOne
    @JoinColumn(name = "doer")
    public User getDoer() {
        return doer;
    }
    public void setDoer(User doer) {
        this.doer = doer;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "log_type")
    public TimeLogType getLogType() {
        return logType;
    }

    public void setLogType(TimeLogType logType) {
        this.logType = logType;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put(BEGIN, begin.toString());
        if (end != null){
            object.put(END, end.toString());
        }
        object.put(DOER, doer.shortJson());
        return object;
    }
}
