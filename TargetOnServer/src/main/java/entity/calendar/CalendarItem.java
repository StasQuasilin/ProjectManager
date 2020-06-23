package entity.calendar;

import entity.task.Task;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "calendar_item")
public class CalendarItem {
    private int id;
    private Date date;
    private Time time;
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
    @Column(name = "date")
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "time")
    public Time getTime() {
        return time;
    }
    public void setTime(Time time) {
        this.time = time;
    }

    @OneToOne
    @JoinColumn(name = "task")
    public Task getTask() {
        return task;
    }
    public void setTask(Task task) {
        this.task = task;
    }
}
