package entity.task;

import entity.finance.category.Category;

import javax.persistence.*;

@Entity
@Table(name = "task_statistic")
public class TaskStatistic {
    private int id;
    private Category category;
    private float plus;
    private float minus;
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

    @OneToOne
    @JoinColumn(name = "_category")
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    @Transient
    public float getTotalCoast() {
        return plus + minus;
    }

    @Basic
    @Column(name = "plus")
    public float getPlus() {
        return plus;
    }
    public void setPlus(float plus) {
        this.plus = plus;
    }

    @Basic
    @Column(name = "minus")
    public float getMinus() {
        return minus;
    }
    public void setMinus(float minus) {
        this.minus = minus;
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
    @Column(name = "active")
    public int getActiveChildren() {
        return activeChildren;
    }
    public void setActiveChildren(int activeChildren) {
        this.activeChildren = activeChildren;
    }

    @Basic
    @Column(name = "progressing")
    public int getProgressingChildren() {
        return progressingChildren;
    }
    public void setProgressingChildren(int progressingChildren) {
        this.progressingChildren = progressingChildren;
    }

    @Basic
    @Column(name = "done")
    public int getDoneChildren() {
        return doneChildren;
    }
    public void setDoneChildren(int doneChildren) {
        this.doneChildren = doneChildren;
    }

    public void clean() {
        plus = 0;
        minus = 0;
        spendTime = 0;
        activeChildren = 0;
        progressingChildren = 0;
        doneChildren = 0;
    }

    public void add(TaskStatistic childrenStat) {
        if (childrenStat != null){
            plus = childrenStat.getPlus();
            minus = childrenStat.getMinus();
            spendTime = childrenStat.getSpendTime();
            activeChildren = childrenStat.getActiveChildren();
            progressingChildren = childrenStat.getProgressingChildren();
            doneChildren = childrenStat.getDoneChildren();
        }
    }

    public void add(TaskStatus status) {
        if(status == TaskStatus.active){
            activeChildren++;
        } else if (status == TaskStatus.progressing){
            progressingChildren++;
        } else if (status == TaskStatus.done){
            doneChildren++;
        }

    }
}
