package entity.goal;

import entity.task.Task;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;
import java.sql.Date;

import static constants.Keys.*;

@Entity
@Table(name = "goals")
public class Goal extends JsonAble {
    private int id;
    private Task task;
    private Date begin;
    private Date end;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "task")
    public Task getTask() {
        return task;
    }
    public void setTask(Task task) {
        this.task = task;
    }

    @Basic
    @Column(name = "begin")
    public Date getBegin() {
        return begin;
    }
    public void setBegin(Date begin) {
        this.begin = begin;
    }

    @Basic
    @Column(name = "end")
    public Date getEnd() {
        return end;
    }
    public void setEnd(Date end) {
        this.end = end;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = task.toJson();
        jsonObject.put(ID, id);
        if (begin != null){
            jsonObject.put(BEGIN, begin.toString());
        }
        if (end != null){
            jsonObject.put(END, end.toString());
        }


        return jsonObject;
    }


}
