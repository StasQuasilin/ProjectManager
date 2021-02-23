package entity.goal;

import entity.Title;
import entity.user.User;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;
import java.sql.Date;

import static constants.Keys.*;

@Entity
@Table(name = "goals")
public class Goal extends JsonAble {
    private int id;
    private Title title;
    private int buyList;
    private Date begin;
    private Date end;
    private float budget;
    private GoalStatus status = GoalStatus.active;

    @Id
    @Column(name = "_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "_title")
    public Title getTitle() {
        return title;
    }
    public void setTitle(Title header) {
        this.title = header;
    }

    @Basic
    @Column(name = "_buy_list")
    public int getBuyList() {
        return buyList;
    }
    public void setBuyList(int buyList) {
        this.buyList = buyList;
    }

    @Basic
    @Column(name = "_begin")
    public Date getBegin() {
        return begin;
    }
    public void setBegin(Date begin) {
        this.begin = begin;
    }

    @Basic
    @Column(name = "_end")
    public Date getEnd() {
        return end;
    }
    public void setEnd(Date end) {
        this.end = end;
    }

    @Basic
    @Column(name = "_budget")
    public float getBudget() {
        return budget;
    }
    public void setBudget(float budget) {
        this.budget = budget;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "_status")
    public GoalStatus getStatus() {
        return status;
    }
    public void setStatus(GoalStatus status) {
        this.status = status;
    }

    @Override
    public JSONObject shortJson() {
        JSONObject jsonObject = getJsonObject();
        jsonObject.put(ID, id);
        jsonObject.put(TITLE_ID, title.getId());
        jsonObject.put(TITLE, title.getValue());
        return jsonObject;
    }

    @Override
    public JSONObject toJson() {

        final JSONObject jsonObject = shortJson();

        if (begin != null){
            jsonObject.put(BEGIN, begin.toString());
        }
        if (end != null){
            jsonObject.put(END, end.toString());
        }

        jsonObject.put(BUDGET, budget);
        jsonObject.put(STATUS, status.toString());

        return jsonObject;
    }

    @Transient
    public User getOwner() {
        return title.getOwner();
    }

    @Override
    public String toString() {
        return "Goal '" + title.getValue() + "'";
    }
}
