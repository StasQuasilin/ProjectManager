package entity.finance.buy;

import javax.persistence.*;

@Entity
@Table(name = "buy_list_items")
public class BuyListItem {
    private int id;
    private BuyList list;
    private String title;
    private float count;
    private float price;

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
}
