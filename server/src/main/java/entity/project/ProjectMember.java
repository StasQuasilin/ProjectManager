package entity.project;

import constants.Keys;
import constants.TableNames;
import entity.user.User;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 17.02.2020.
 */
@Entity
@Table(name = TableNames.PROJECT_MEMBERS)
public class ProjectMember implements Keys {
    private int id;
    private Project project;
    private User member;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = PROJECT)
    public Project getProject() {
        return project;
    }
    public void setProject(Project project) {
        this.project = project;
    }

    @OneToOne
    @JoinColumn(name = MEMBER)
    public User getMember() {
        return member;
    }
    public void setMember(User member) {
        this.member = member;
    }
}
