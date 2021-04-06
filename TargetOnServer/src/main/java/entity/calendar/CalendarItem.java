package entity.calendar;

import entity.finance.category.Header;
import entity.user.User;
import org.json.simple.JSONObject;
import utils.Math;
import utils.json.JsonAble;
import utils.json.JsonObject;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

import static constants.Keys.*;

@Entity
@Table(name = "calendar_item")
public class CalendarItem extends JsonAble {
    private int id;
    private Header header;
    private Date date;
    private Time time;
    private long length;
    private ExecutionStatus status;
    private Repeat repeat = Repeat.none;
    private int weekDays;
    private boolean notify = false;
    private long notifyPeriod = 0;
    private User owner;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "_date_begin")
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "_time_begin")
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

    @Basic
    @Column(name = "_week_days")
    public int getWeekDays() {
        return weekDays;
    }
    public void setWeekDays(int weekDays) {
        this.weekDays = weekDays;
    }

    @OneToOne
    @JoinColumn(name = "_category")
    public Header getHeader() {
        return header;
    }
    public void setHeader(Header header) {
        this.header = header;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "_status")
    public ExecutionStatus getStatus() {
        return status;
    }
    public void setStatus(ExecutionStatus status) {
        this.status = status;
    }

    @Basic
    @Column(name = "_notify")
    public boolean isNotify() {
        return notify;
    }
    public void setNotify(boolean notify) {
        this.notify = notify;
    }

    @Basic
    @Column(name = "_notify_period")
    public long getNotifyPeriod() {
        return notifyPeriod;
    }
    public void setNotifyPeriod(long notifyPeriod) {
        this.notifyPeriod = notifyPeriod;
    }

    @OneToOne
    @JoinColumn(name = "_owner")
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public JSONObject toJson() {
        final JSONObject json = new JSONObject();
        json.put(ID, id);
        json.put(HEADER, header.getId());
        json.put(TITLE, header.getTitle());
        if (date != null){
            json.put(DATE, date.toString());
            if (time != null){
                json.put(TIME, time.toString());
                json.put(LENGTH, length);
            }
        }

        json.put(REPEAT, repeat.toString());
        if (header != null && header.getParent() != null){
            json.put(PARENT, header.getParent().toJson());
        }

        return json;
    }
}
