package entity.finance.buy;

import entity.user.User;
import utils.json.JsonAble;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "buy_list")
public class BuyList extends JsonAble {
    private int id;
    private String title;
    private User owner;
    private Set<BuyListItem> itemSet = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @OneToOne
    @JoinColumn(name = "owner")
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy="list", cascade = CascadeType.ALL)
    public Set<BuyListItem> getItemSet() {
        return itemSet;
    }
    public void setItemSet(Set<BuyListItem> itemSet) {
        this.itemSet = itemSet;
    }

    public void clearItems() {
        itemSet.clear();
    }

    public void addItem(BuyListItem item) {
        itemSet.add(item);
    }
}
