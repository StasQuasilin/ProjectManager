package entity.goal;

import entity.finance.category.Category;
import entity.task.TaskStatistic;
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
    private Category category;
    private TaskStatistic statistic;
    private Date begin;
    private Date end;
    private float budget;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "category")
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "category", cascade = CascadeType.ALL)
    public TaskStatistic getStatistic() {
        return statistic;
    }
    public void setStatistic(TaskStatistic statistic) {
        this.statistic = statistic;
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
    @Column(name = "budget")
    public float getBudget() {
        return budget;
    }
    public void setBudget(float budget) {
        this.budget = budget;
    }

    @Override
    public JSONObject toJson() {

        JSONObject jsonObject = getJsonObject();
        jsonObject.put(ID, id);
        jsonObject.put(CATEGORY, category.getId());
        jsonObject.put(TITLE, category.getTitle());

        if (begin != null){
            jsonObject.put(BEGIN, begin.toString());
        }
        if (end != null){
            jsonObject.put(END, end.toString());
        }

        if (statistic != null){
            jsonObject.put(STATISTIC, statistic.toJson());
        }
        jsonObject.put(BUDGET, budget);

        return jsonObject;
    }

    @Transient
    public User getOwner() {
        return category.getOwner();
    }
}
