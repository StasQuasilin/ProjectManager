package entity.social;

import entity.user.User;

import javax.persistence.*;

@Entity
@Table(name = "friendships")
public class Friendship {
    private int id;
    private User owner;
    private User friend;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "_owner")
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }

    @OneToOne
    @JoinColumn(name = "_friend")
    public User getFriend() {
        return friend;
    }
    public void setFriend(User friend) {
        this.friend = friend;
    }
}
