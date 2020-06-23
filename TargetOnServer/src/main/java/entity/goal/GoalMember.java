package entity.goal;

import entity.user.User;

import javax.persistence.*;

@Entity
@Table(name = "goal_members")
public class GoalMember {
    private int id;
    private Goal goal;
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
    @JoinColumn(name = "goal")
    public Goal getGoal() {
        return goal;
    }
    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    @OneToOne
    @JoinColumn(name = "member")
    public User getMember() {
        return member;
    }
    public void setMember(User member) {
        this.member = member;
    }
}
