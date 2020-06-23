package entity.task;

import javax.persistence.*;

@Entity
@Table(name = "task_statistic")
public class TaskStatistic {
    private int id;
    private String taskUid;
    private long spendTime;
    private int activeChildren;
    private int progressingChildren;
    private int doneChildren;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "task_uid")
    public String getTaskUid() {
        return taskUid;
    }
    public void setTaskUid(String taskUid) {
        this.taskUid = taskUid;
    }

    @Basic
    @Column(name = "spend_time")
    public long getSpendTime() {
        return spendTime;
    }
    public void setSpendTime(long spendTime) {
        this.spendTime = spendTime;
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
}
