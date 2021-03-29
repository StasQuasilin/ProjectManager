package entity.finance.buy;

import entity.Title;
import entity.user.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static constants.Keys.ID;
import static constants.Keys.ITEMS;
import static constants.Keys.TITLE;

@Entity
@Table(name = "buy_list")
public class BuyList extends JsonAble {
    private int id;
    private Title title;
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

    @OneToOne
    @JoinColumn(name = "title")
    public Title getTitle() {
        return title;
    }
    public void setTitle(Title title) {
        this.title = title;
    }

    @OneToOne
    @JoinColumn(name = "_owner")
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

    @Override
    public JSONObject shortJson() {
        JSONObject json  = getJsonObject();
        json.put(ID, id);
        json.put(TITLE, title.getValue());
        return json;
    }

    @Override
    public JSONObject toJson() {
        final JSONObject json = shortJson();
        json.put(ITEMS, items());
        return json;
    }

    private JSONArray items() {
        JSONArray array = new JSONArray();
        for (BuyListItem item : itemSet){
            array.add(item.toJson());
        }
        return array;
    }
}
