package entity.task;

import entity.finance.accounts.PointScale;
import entity.finance.category.Category;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "task_settings")
public class TaskSettings {
    private int id;
    private Category category;
    private PointScale repeatEach;
    private Date nextRepeat;
    private int repeatCount;
    private Task activeAfterDone;
    private boolean doneIfChildren;
    private TaskType type;
    private OnFailure onFailure;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "_category")
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
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
    @JoinColumn(name = "active_after")
    public Task getActiveAfterDone() {
        return activeAfterDone;
    }
    public void setActiveAfterDone(Task activeAfterDone) {
        this.activeAfterDone = activeAfterDone;
    }

    @Basic
    @Column(name = "done_if_children_complete")
    public boolean isDoneIfChildren() {
        return doneIfChildren;
    }
    public void setDoneIfChildren(boolean doneIfChildren) {
        this.doneIfChildren = doneIfChildren;
    }

    @Basic
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "_type")
    public TaskType getType() {
        return type;
    }
    public void setType(TaskType type) {
        this.type = type;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "on_failure")
    public OnFailure getOnFailure() {
        return onFailure;
    }
    public void setOnFailure(OnFailure onFailure) {
        this.onFailure = onFailure;
    }
}
