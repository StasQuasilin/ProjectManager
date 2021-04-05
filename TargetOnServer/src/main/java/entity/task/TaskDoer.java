package entity.task;

import entity.user.User;

import javax.persistence.*;

@Entity
@Table(name = "task_doers")
public class TaskDoer {
    private int id;
    private Task task;
    private User doer;
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

    @OneToOne
    @JoinColumn(name = "_doer")
    public User getDoer() {
        return doer;
    }
    public void setDoer(User doer) {
        this.doer = doer;
    }
}
