package entity.task;

import javax.persistence.*;
import java.sql.Timestamp;
@Entity
@Table(name = "timer_data")
public class TimerData {
    private int id;
    private Task task;
    private Timestamp begin;
    private long spend;
    private TimerType type;

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
    @Column(name = "_begin")
    public Timestamp getBegin() {
        return begin;
    }
    public void setBegin(Timestamp begin) {
        this.begin = begin;
    }

    @Basic
    @Column(name = "spend")
    public long getSpend() {
        return spend;
    }
    public void setSpend(long spend) {
        this.spend = spend;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "_type")
    public TimerType getType() {
        return type;
    }
    public void setType(TimerType type) {
        this.type = type;
    }
}
