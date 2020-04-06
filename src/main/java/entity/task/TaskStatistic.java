package entity.task;

import entity.project.Task;

import javax.persistence.*;

@Entity
@Table(name = "task_statistic")
public class TaskStatistic {
    private int id;
    private Task task;
    private int activeChildren;
    private int progressingChildren;
    private int doneChildren;
    private int spendTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "task")
    public Task getTask() {
        return task;
    }
    public void setTask(Task task) {
        this.task = task;
    }

    @Basic
    @Column(name = "active_children")
    public int getActiveChildren() {
        return activeChildren;
    }
    public void setActiveChildren(int activeChildren) {
        this.activeChildren = activeChildren;
    }

    @Basic
    @Column(name = "progressing_children")
    public int getProgressingChildren() {
        return progressingChildren;
    }
    public void setProgressingChildren(int progressingChildren) {
        this.progressingChildren = progressingChildren;
    }

    @Basic
    @Column(name = "done_children")
    public int getDoneChildren() {
        return doneChildren;
    }
    public void setDoneChildren(int doneChildren) {
        this.doneChildren = doneChildren;
    }

    @Basic
    @Column(name = "spend_time")
    public int getSpendTime() {
        return spendTime;
    }
    public void setSpendTime(int spendTime) {
        this.spendTime = spendTime;
    }
}
