package entity.task;

import entity.user.User;
import org.json.simple.JSONObject;

import javax.persistence.*;

import static constants.Keys.ID;
import static constants.Keys.TITLE;

@Entity
@Table(name = "tasks")
public class Category extends ITask {
    private int id;
    private String title;
    private Category parent;
    private User owner;

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
    @JoinColumn(name = "parent")
    public Category getParent() {
        return parent;
    }
    public void setParent(Category parent) {
        super.setParent(parent);
        this.parent = parent;
    }

    @OneToOne
    @JoinColumn(name = "owner")
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put(TITLE, title);

        return object;
    }
}
