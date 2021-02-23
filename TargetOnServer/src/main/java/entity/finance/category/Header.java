package entity.finance.category;

import constants.Keys;
import entity.user.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;

@Entity
@Table(name = "headers")
public class Header extends JsonAble {
    private int id;
    private String title;
    private Header parent;
    private User owner;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "_title")
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @OneToOne
    @JoinColumn(name = "_parent")
    public Header getParent() {
        return parent;
    }
    public void setParent(Header parent) {
        this.parent = parent;
    }

    @OneToOne
    @JoinColumn(name = "_owner")
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }


    @Override
    public JSONObject toJson() {
        final JSONObject object = new JSONObject();
        object.put(Keys.ID, id);
        object.put(Keys.TITLE, title);
        object.put(Keys.PATH, buildPath());
        return object;
    }

    private JSONArray buildPath() {
        JSONArray array = new JSONArray();
        if(parent != null){
            array.addAll(parent.buildPath());
        }
        array.add(toJson());
        return array;
    }
}
