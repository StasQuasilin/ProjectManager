package entity.budget;

import constants.Tables;
import entity.user.User;

import javax.persistence.*;

import static constants.Keys.NAME;
import static constants.Keys.OWNER;

@Entity
@Table(name = Tables.COUNTERPARTYES)
public class Counterparty {
    private int id;
    private User owner;
    private String name;

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
    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Basic
    @Column(name = NAME)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
