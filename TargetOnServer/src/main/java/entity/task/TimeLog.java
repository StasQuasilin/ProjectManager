package entity.task;

import entity.finance.category.Header;
import entity.user.User;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;
import java.sql.Timestamp;

import static constants.Keys.*;

@Entity
@Table(name = "time_log")
public class TimeLog extends JsonAble {
    private int id;
    private Header header;
    private Timestamp begin;
    private long length;
    private LogType logType = LogType.log;
    private User owner;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "_task")
    public Header getHeader() {
        return header;
    }
    public void setHeader(Header header) {
        this.header = header;
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
    @Column(name = "_length")
    public long getLength() {
        return length;
    }
    public void setLength(long length) {
        this.length = length;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "_type")
    public LogType getLogType() {
        return logType;
    }
    public void setLogType(LogType logType) {
        this.logType = logType;
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
        final JSONObject jsonObject = getJsonObject();
        jsonObject.put(ID, id);
        jsonObject.put(TASK, header.shortJson());
        jsonObject.put(BEGIN, begin.toString());
        jsonObject.put(LENGTH, length);
        jsonObject.put(TYPE, logType.toString());
        jsonObject.put(OWNER, owner.toJson());
        return jsonObject;
    }

    public void setEnd(Timestamp end) {
        length = (end.getTime() - begin.getTime()) / 1000;
    }
}
