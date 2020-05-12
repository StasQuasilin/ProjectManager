package entity.project;

import constants.Keys;
import constants.TableNames;
import entity.task.Task;
import entity.user.User;
import org.json.simple.JSONObject;
import utils.JsonAble;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by quasilin on 24.02.2019.
 */
@Entity
@Table(name = TableNames.PROJECTS)
public class Project extends JsonAble implements Keys {
    private int id;
    private User owner;
    private Date beginDate;
    private Date completeDate;
    private String description;
    private Task task;
    private ProjectType type;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = OWNER)
    public User getOwner() {
        return owner;
    }
    public void setOwner(User creator) {
        this.owner = creator;
    }

    @Basic
    @Column(name = PROJECT_BEGIN)
    public Date getBeginDate() {
        return beginDate;
    }
    public void setBeginDate(Date date) {
        this.beginDate = date;
    }

    @Basic
    @Column(name = PROJECT_END)
    public Date getCompleteDate() {
        return completeDate;
    }
    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    @Basic
    @Column(name = DESCRIPTION)
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @OneToOne
    @JoinColumn(name = TASK)
    public Task getTask() {
        return task;
    }
    public void setTask(Task task) {
        this.task = task;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = TYPE)
    public ProjectType getType() {
        return type;
    }
    public void setType(ProjectType type) {
        this.type = type;
    }


    @Transient
    public float getTimeProgress() {
        long b = beginDate.getTime();
        long n = Date.valueOf(LocalDate.now()).getTime();
        long c = completeDate.getTime();
        return 1f * (n - b) / (c - n);
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = task.toJson();
        object.put(ID, id);
        object.put(BEGIN, beginDate.toString());
        object.put(END, completeDate.toString());
        object.put(DESCRIPTION, description);
        return object;
    }
}
