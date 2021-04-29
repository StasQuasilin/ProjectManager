package entity.finance.category;

import entity.Title;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;

import static constants.Keys.*;

@Entity
@Table(name = "title_coasts")
public class TitleCost extends JsonAble {
    private int id;
    private Title title;
    private float alreadySpend;
    private float totalCost;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id")
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
    public void setTitle(Title title) {
        this.title = title;
    }



    @Basic
    @Column(name = "_already_spend")
    public float getAlreadySpend() {
        return alreadySpend;
    }
    public void setAlreadySpend(float alreadySpend) {
        this.alreadySpend = alreadySpend;
    }

    @Basic
    @Column(name = "_total_coast")
    public float getTotalCost() {
        return totalCost;
    }
    public void setTotalCost(float totalCoast) {
        this.totalCost = totalCoast;
    }

    @Override
    public JSONObject toJson() {
        final JSONObject object = new JSONObject();
        object.put(ID, id);
        object.put(SPEND, alreadySpend);
        object.put(COST, totalCost);
        return object;
    }
}
