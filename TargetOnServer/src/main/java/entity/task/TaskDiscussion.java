package entity.task;

import entity.user.User;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "task_discussions")
public class TaskDiscussion {
    private int id;
    private Task task;
    private Timestamp time;
    private User author;
    private TaskDiscussion parent;
    private String text;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "_task")
    public Task getTask() {
        return task;
    }
    public void setTask(Task task) {
        this.task = task;
    }

    @Basic
    @Column(name = "_time")
    public Timestamp getTime() {
        return time;
    }
    public void setTime(Timestamp time) {
        this.time = time;
    }

    @OneToOne
    @JoinColumn(name = "_author")
    public User getAuthor() {
        return author;
    }
    public void setAuthor(User author) {
        this.author = author;
    }

    @OneToOne
    @JoinColumn(name = "_parent")
    public TaskDiscussion getParent() {
        return parent;
    }
    public void setParent(TaskDiscussion parent) {
        this.parent = parent;
    }

    @Basic
    @Column(name = "_text")
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
