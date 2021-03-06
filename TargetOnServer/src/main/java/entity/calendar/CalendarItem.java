package entity.calendar;

import entity.finance.category.Header;
import org.json.simple.JSONObject;
import utils.Math;
import utils.json.JsonAble;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

import static constants.Keys.*;

@Entity
@Table(name = "calendar_item")
public class CalendarItem extends JsonAble {
    private int id;
    private Date date;
    private Time time;
    private Date endDate;
    private Time endTime;
    private Header header;
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
    @Column(name = "_date_end")
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "_time_end")
    public Time getEndTime() {
        return endTime;
    }
    public void setEndTime(Time endTime) {
        this.endTime = endTime;
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

    @Override
    public JSONObject toJson() {
        final JSONObject json = header.toJson();
        json.put(ID, id);
        json.put(CATEGORY, header.shortJson());
        if (date != null){
            json.put(DATE, date.toString());
            if (time != null){
                json.put(TIME, time.toString());
                json.put(LENGTH, length());
            }
        }

        json.put(REPEAT, repeat.toString());

        return json;
    }

    private long length() {
        if (date != null && time != null && endDate != null && endTime != null){
            LocalDateTime from = LocalDateTime.of(date.toLocalDate(), time.toLocalTime());
            LocalDateTime to = LocalDateTime.of(endDate.toLocalDate(), endTime.toLocalTime());
            return Math.twoDatesDifference(to, from) / 1000 / 60;
        }
        return 0;
    }
}
