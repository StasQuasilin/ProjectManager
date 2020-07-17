package entity.goal;

import entity.finance.category.Category;
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
    @JoinColumn(name = "category")
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
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

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = category.shortJson();

        if (begin != null){
            jsonObject.put(BEGIN, begin.toString());
        }
        if (end != null){
            jsonObject.put(END, end.toString());
        }

        return jsonObject;
    }

    @Transient
    public User getOwner() {
        return category.getOwner();
    }
}
