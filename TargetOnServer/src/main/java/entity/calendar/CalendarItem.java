package entity.calendar;

import entity.finance.Category;
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
    private long length;
    private Category category;
    private Repeat repeat = Repeat.none;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
    @Column(name = "_time")
    public Time getTime() {
        return time;
    }
    public void setTime(Time time) {
        this.time = time;
    }

    @Basic
    @Column(name = "_length")
    public long getLength() {
        return length;
    }
    public void setLength(long length) {
        this.length = length;
    }

    @OneToOne
    @JoinColumn(name = "_category")
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "repeat")
    public Repeat getRepeat() {
        return repeat;
    }
    public void setRepeat(Repeat repeat) {
        this.repeat = repeat;
    }
}
