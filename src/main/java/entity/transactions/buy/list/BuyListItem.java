package entity.transactions.buy.list;

import javax.persistence.*;

@Entity
@Table(name = "buy_list_items")
public class BuyListItem {
    private int id;
    private BuyList buyList;
    private String title;
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
}
