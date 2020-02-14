package entity.project;

import com.sun.istack.NotNull;
import constants.Keys;
import entity.user.User;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 25.02.2019.
 */
@Entity
@Table(name = "tasks")
public class Task implements Comparable<Task>, Keys {
    private int id;
    private TaskStatus status;
    private Task parent;
    private String title;
    private User owner;

    public Task() {}

    public Task(String title) {
        status = TaskStatus.active;
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

    @Enumerated(EnumType.STRING)
    @Column(name = STATUS)
    public TaskStatus getStatus() {
        return status;
    }
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @OneToOne
    @JoinColumn(name = PARENT)
    public Task getParent() {
        return parent;
    }
    public void setParent(Task parent) {
        this.parent = parent;
    }

    @Basic
    @Column(name = TITLE)
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @OneToOne
    @JoinColumn(name = OWNER)
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public int compareTo(Task o) {
        return o.getTitle().compareTo(title);
    }
}
