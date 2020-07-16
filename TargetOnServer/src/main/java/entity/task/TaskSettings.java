package entity.task;

import entity.finance.accounts.PointScale;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "task_settings")
public class TaskSettings {
    private int id;
    private String taskUid;
    private PointScale repeatEach;
    private Date nextRepeat;
    private int repeatCount;
    private Task activeAfterDone;
    private boolean doneIfChildren;
    private TaskType type;

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
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "repeat_each")
    public PointScale getRepeatEach() {
        return repeatEach;
    }
    public void setRepeatEach(PointScale repeatEach) {
        this.repeatEach = repeatEach;
    }

    @Basic
    @Column(name = "next_repeat")
    public Date getNextRepeat() {
        return nextRepeat;
    }
    public void setNextRepeat(Date nextRepeat) {
        this.nextRepeat = nextRepeat;
    }

    @Basic
    @Column(name = "repeat_count")
    public int getRepeatCount() {
        return repeatCount;
    }
    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }

    @OneToOne
    @JoinColumn(name = "active_after_done")
    public Task getActiveAfterDone() {
        return activeAfterDone;
    }
    public void setActiveAfterDone(Task activeAfterDone) {
        this.activeAfterDone = activeAfterDone;
    }

    @Basic
    @Column(name = "done_if_children")
    public boolean isDoneIfChildren() {
        return doneIfChildren;
    }
    public void setDoneIfChildren(boolean doneIfChildren) {
        this.doneIfChildren = doneIfChildren;
    }

    @Basic
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    public TaskType getType() {
        return type;
    }
    public void setType(TaskType type) {
        this.type = type;
    }
}
