package entity;

import entity.task.TaskStatus;
import entity.project.iTask;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.util.Set;

import static constants.Keys.*;

@Entity
@Table(name = "tasks")
public class TreeItem extends iTask {
    private int id;
    private TaskStatus status;
    private String title;
    private TreeItem parent;
    private Set<TreeItem> children;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    public TaskStatus getStatus() {
        return status;
    }
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToOne
    @JoinColumn(name = "parent")
    public TreeItem getParent() {
        return parent;
    }
    public void setParent(TreeItem parent) {
        this.parent = parent;
        super.setParent(parent);
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parent", cascade = CascadeType.ALL)
    public Set<TreeItem> getChildren() {
        return children;
    }
    public void setChildren(Set<TreeItem> children) {
        this.children = children;
    }

    @Override
    public JSONObject shortJson() {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put(STATUS, status.toString());
        object.put(TITLE, title);
        object.put(CHILDREN, children());
        return object;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = shortJson();
//        object.put(PATH, buildPath());
        return object;
    }

    private JSONArray children() {
        JSONArray array = pool.getArray();
        for (TreeItem item : children){
            array.add(item.toJson());
        }
        return array;
    }
}
