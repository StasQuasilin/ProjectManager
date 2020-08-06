package entity.finance.buy;

import entity.finance.category.Category;
import entity.task.Unit;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;

import static constants.Keys.*;

@Entity
@Table(name = "buy_list_items")
public class BuyListItem extends JsonAble {
    private int id;
    private BuyList list;
    private Category category;
    private float count;
    private Unit unit;
    private float price;
    private String currency;
    private boolean done;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "_list")
    public BuyList getList() {
        return list;
    }
    public void setList(BuyList buyList) {
        this.list = buyList;
    }

    @OneToOne
    @JoinColumn(name = "_category")
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    @Basic
    @Column(name = "count")
    public float getCount() {
        return count;
    }
    public void setCount(float count) {
        this.count = count;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "_unit")
    public Unit getUnit() {
        return unit;
    }
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Basic
    @Column(name = "price")
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    @Basic
    @Column(name = "currency")
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Basic
    @Column(name = "done")
    public boolean isDone() {
        return done;
    }
    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = getJsonObject();
        json.put(ID, id);
        json.put(CATEGORY, category.getId());
        json.put(TITLE, category.getTitle());
        json.put(COUNT, count);
        json.put(UNIT, unit.toString());
        json.put(PRICE, price);
        json.put(CURRENCY, currency);
        json.put(DONE, done);
        return json;
    }
}
