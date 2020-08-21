package entity.finance.category;

import entity.task.TaskStatistic;
import entity.user.User;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;

import static constants.Keys.*;

@Entity
@Table(name = "categories")
public class Category extends JsonAble {
    private int id;
    private String title;
    private Category parent;
    private boolean hidden;
    private User owner;
    private String currency;
    private TaskStatistic statistic;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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
    @JoinColumn(name = "parent")
    public Category getParent() {
        return parent;
    }
    public void setParent(Category parent) {
        if (parent != null){
            final String currency = parent.getCurrency();
            if (currency != null){
                this.currency = currency;
            }
        }
        this.parent = parent;
    }

    @Basic
    @Column(name = "hidden")
    public boolean isHidden() {
        return hidden;
    }
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    @OneToOne
    @JoinColumn(name = "_owner")
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Basic
    @Column(name = "currency")
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "category", cascade = CascadeType.ALL)
    public TaskStatistic getStatistic() {
        return statistic;
    }
    public void setStatistic(TaskStatistic statistic) {
        this.statistic = statistic;
    }

    @Override
    public JSONObject shortJson() {
        JSONObject jsonObject = getJsonObject();
        jsonObject.put(ID, id);
        jsonObject.put(TITLE, title);
        if (currency != null) {
            jsonObject.put(CURRENCY, currency);
        }
        if (parent != null){
            jsonObject.put(PARENT, parent.shortJson());
        }
        return jsonObject;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = shortJson();

        jsonObject.put(OWNER, owner.toJson());
        return jsonObject;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return getClass().equals(obj.getClass()) && hashCode() == obj.hashCode();
    }
}
