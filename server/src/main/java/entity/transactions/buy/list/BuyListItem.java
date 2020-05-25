package entity.transactions.buy.list;

import entity.transactions.TransactionCategory;
import org.json.simple.JSONObject;
import utils.JsonAble;
import static constants.Keys.*;
import javax.persistence.*;

@Entity
@Table(name = "buy_list_items")
public class BuyListItem extends JsonAble {
    private int id;
    private BuyList buyList;
    private String title;
    private float price;
    private TransactionCategory category;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "buy_list")
    public BuyList getBuyList() {
        return buyList;
    }
    public void setBuyList(BuyList buyList) {
        this.buyList = buyList;
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
    @Column(name = "price")
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    @OneToOne
    @JoinColumn(name = "category")
    public TransactionCategory getCategory() {
        return category;
    }
    public void setCategory(TransactionCategory category) {
        this.category = category;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put(TITLE, title);
        object.put(PRICE, price);
        return object;
    }
}
