package entity;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 25.02.2019.
 */
@Entity
@Table(name = "tasks")
public class Task implements Comparable<Task>{
    private int id;
    private String status;
    private Task parent;
    private String title;

    public Task() {}

    public Task(String title) {
        status = TaskStatus.active.toString();
        this.title = title;
    }

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @OneToOne
    @JoinColumn(name = "parent")
    public Task getParent() {
        return parent;
    }
    public void setParent(Task parent) {
        this.parent = parent;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int compareTo(Task o) {
        return o.getTitle().compareTo(title);
    }
}
