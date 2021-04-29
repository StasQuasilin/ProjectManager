package entity.task;

import entity.user.User;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "task_file")
public class TaskFile {
    private int id;
    private Task task;
    private String file;
    private String fileName;
    private Timestamp timestamp;
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "_task")
    public Task getTask() {
        return task;
    }
    public void setTask(Task task) {
        this.task = task;
    }

    @Basic
    @Column(name = "_file")
    public String getFile() {
        return file;
    }
    public void setFile(String file) {
        this.file = file;
    }

    @Basic
    @Column(name = "_file_name")
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Basic
    @Column(name = "_timestamp")
    public Timestamp getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @OneToOne
    @JoinColumn(name = "_user")
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
