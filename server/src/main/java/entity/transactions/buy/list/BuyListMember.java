package entity.transactions.buy.list;

import entity.user.User;

import javax.persistence.*;

@Entity
@Table(name = "buy_list_member")
public class BuyListMember {
    private int id;
    private BuyList list;
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
    @JoinColumn(name = "list")
    public BuyList getList() {
        return list;
    }
    public void setList(BuyList list) {
        this.list = list;
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
