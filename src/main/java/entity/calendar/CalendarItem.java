package entity.calendar;

import entity.project.Task;
import org.json.simple.JSONObject;
import utils.JsonAble;

import javax.persistence.*;
import java.sql.Timestamp;

import static constants.Keys.*;

@Entity
@Table(name = "calendar_items")
public class CalendarItem extends JsonAble {
    private int id;
    private Timestamp begin;
    private Timestamp end;
    private Task task;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "begin")
    public Timestamp getBegin() {
        return begin;
    }
    public void setBegin(Timestamp timestamp) {
        this.begin = timestamp;
    }

    @Basic
    @Column(name = "_end")
    public Timestamp getEnd() {
        return end;
    }
    public void setEnd(Timestamp end) {
        this.end = end;
    }

    @OneToOne
    @JoinColumn(name = "task")
    public Task getTask() {
        return task;
    }
    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put(BEGIN, begin.toString());
        object.put(END, end.toString());
        object.put(TASK, task.shortJson());
        return object;
    }
}
