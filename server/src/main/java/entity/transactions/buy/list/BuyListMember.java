package entity.transactions.buy.list;

import entity.user.User;
import org.json.simple.JSONObject;
import utils.JsonAble;
import static constants.Keys.*;

import javax.persistence.*;

@Entity
@Table(name = "buy_list_member")
public class BuyListMember extends JsonAble {
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

    @ManyToOne
    @JoinColumn(name = "list")
    public BuyList getList() {
        return list;
    }
    public void setList(BuyList list) {
        this.list = list;
    }

    @ManyToOne
    @JoinColumn(name = "member")
    public User getMember() {
        return member;
    }
    public void setMember(User member) {
        this.member = member;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put(MEMBER, member.toJson());
        return object;
    }
}
