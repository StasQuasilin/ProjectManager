package entity.calendar;

import entity.finance.category.Category;
import entity.task.Task;
import entity.user.User;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import static constants.Keys.*;

@Entity
@Table(name = "calendar_item")
public class CalendarItem extends JsonAble {
    private int id;
    private Date date;
    private Time time;
    private long length;
    private Category category;
    private ExecutionStatus status;
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

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "repeat")
    public Repeat getRepeat() {
        return repeat;
    }
    public void setRepeat(Repeat repeat) {
        this.repeat = repeat;
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
    @Column(name = "_status")
    public ExecutionStatus getStatus() {
        return status;
    }
    public void setStatus(ExecutionStatus status) {
        this.status = status;
    }

    @Override
    public JSONObject toJson() {
        final JSONObject json = category.toJson();
        json.put(ID, id);
        json.put(CATEGORY, category.getId());
        if (date != null){
            json.put(DATE, date.toString());
        }
        if (time != null){
            json.put(TIME, time.toString());
            json.put(LENGTH, length);
        }
        json.put(REPEAT, repeat.toString());

        return json;
    }

    @Transient
    public User getOwner() {
        return category.getOwner();
    }
}
