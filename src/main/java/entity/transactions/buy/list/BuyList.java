package entity.transactions.buy.list;

import entity.user.User;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "buy_list")
public class BuyList {
    private int id;
    private String title;
    private Date date;
    private String description;
    private Set<BuyListItem> items;
    private Set<BuyListMember> members;
    private User owner;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "date")
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy="buyList", cascade = CascadeType.ALL)
    public Set<BuyListItem> getItems() {
        return items;
    }
    public void setItems(Set<BuyListItem> items) {
        this.items = items;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy="list", cascade = CascadeType.ALL)
    public Set<BuyListMember> getMembers() {
        return members;
    }
    public void setMembers(Set<BuyListMember> members) {
        this.members = members;
    }

    @OneToOne
    @JoinColumn(name = "owner")
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }
}
