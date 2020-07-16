package entity.finance.buy;

import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;

import static constants.Keys.*;

@Entity
@Table(name = "buy_list_items")
public class BuyListItem extends JsonAble {
    private int id;
    private BuyList list;
    private String title;
    private float count;
    private float price;
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
    @JoinColumn(name = "list")
    public BuyList getList() {
        return list;
    }
    public void setList(BuyList buyList) {
        this.list = buyList;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "count")
    public float getCount() {
        return count;
    }
    public void setCount(float count) {
        this.count = count;
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
        json.put(TITLE, title);
        json.put(COUNT, count);
        json.put(PRICE, price);
        json.put(DONE, done);
        return json;
    }
}
