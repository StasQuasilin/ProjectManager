package entity.task;

import entity.finance.category.Category;
import entity.finance.category.Header;
import org.json.simple.JSONObject;
import utils.finances.PlusMinus;
import utils.json.JsonAble;

import javax.persistence.*;

import static constants.Keys.*;

@Entity
@Table(name = "task_statistic")
public class TaskStatistic extends JsonAble {
    private int id;
    private Header header;
    private float plus;
    private float minus;
    private long spendTime;
    private int activeChildren;
    private int progressingChildren;
    private int doneChildren;
    private int otherChildren;

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
    public Header getHeader() {
        return header;
    }
    public void setHeader(Header header) {
        this.header = header;
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

    @Basic
    @Column(name = "_other")
    public int getOtherChildren() {
        return otherChildren;
    }
    public void setOtherChildren(int otherChildren) {
        this.otherChildren = otherChildren;
    }

    public void clean() {
        plus = 0;
        minus = 0;
    }

    public void add(TaskStatistic statistic) {
        if (statistic != null){
            plus += statistic.getPlus();
            minus += statistic.getMinus();
            activeChildren += statistic.getActiveChildren();
            progressingChildren += statistic.getProgressingChildren();
            doneChildren += statistic.getDoneChildren();
            otherChildren += statistic.getOtherChildren();
        }
    }

    public void add(TaskStatus status, TaskStatistic statistic) {
        if(status == TaskStatus.active){
            activeChildren++;
        } else if (status == TaskStatus.progressing){
            progressingChildren++;
        } else if (status == TaskStatus.done){
            doneChildren++;
        } else {
            otherChildren++;
        }
        if (statistic != null) {
            activeChildren += statistic.getActiveChildren();
            progressingChildren += statistic.getProgressingChildren();
            doneChildren += statistic.getDoneChildren();
        }
    }

    @Override
    public JSONObject toJson() {
        final JSONObject jsonObject = getJsonObject();
        jsonObject.put(PLUS, plus);
        jsonObject.put(MINUS, minus);
        jsonObject.put(SPEND, spendTime);
        jsonObject.put(ACTIVE, activeChildren);
        jsonObject.put(PROGRESSING, progressingChildren);
        jsonObject.put(DONE, doneChildren);
        jsonObject.put(OTHER, otherChildren);
        return jsonObject;
    }

    public void cleanChildren() {
        activeChildren = 0;
        progressingChildren = 0;
        doneChildren = 0;
        otherChildren = 0;
    }

    public void plusActiveChild() {
        activeChildren++;
    }

    public void plusProgressingChild() {
        progressingChildren++;
    }

    public void plusDoneChildren() {
        doneChildren++;
    }

    public boolean any() {
        return plus != 0 || minus != 0 || (spendTime + activeChildren + progressingChildren + doneChildren + otherChildren) > 0;
    }

    public void add(PlusMinus plusMinus) {
        plus += plusMinus.plus;
        minus += plusMinus.minus;
    }

    public void plusOtherChild() {
        otherChildren++;
    }
}
