package entity.task;

import javax.persistence.*;

@Entity
@Table(name = "object_property")
public class ObjectProperty {
    private int id;
    private int title;
    private String key;
    private float oldValue;
    private float newValue;

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
    public int getTitle() {
        return title;
    }
    public void setTitle(int title) {
        this.title = title;
    }

    @Basic
    @Column(name = "_key")
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    @Basic
    @Column(name = "_old_value")
    public float getOldValue() {
        return oldValue;
    }
    public void setOldValue(float oldValue) {
        this.oldValue = oldValue;
    }

    @Basic
    @Column(name = "_new_value")
    public float getNewValue() {
        return newValue;
    }
    public void setNewValue(float newValue) {
        this.newValue = newValue;
    }
}
