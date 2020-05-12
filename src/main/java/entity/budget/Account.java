package entity.budget;

import constants.Keys;
import entity.user.User;
import org.json.simple.JSONObject;
import utils.JsonAble;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 25.02.2019.
 */
@Entity
@Table(name = "accounts")
public class Account extends JsonAble implements Keys {

    private int id;
    private String title;
    private BudgetSize budgetSize;
    private AccountType accountType;
    private float budgetSum;
    private float limit;
    private Currency currency;
    private User owner;
    private Timestamp create;
    private boolean isPublic;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = TITLE)
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = SIZE)
    public BudgetSize getBudgetSize() {
        return budgetSize;
    }
    public void setBudgetSize(BudgetSize budgetSize) {
        this.budgetSize = budgetSize;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = TYPE)
    public AccountType getAccountType() {
        return accountType;
    }
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    @Basic
    @Column(name = SUM)
    public float getBudgetSum() {
        return budgetSum;
    }
    public void setBudgetSum(float budgetSum) {
        this.budgetSum = budgetSum;
    }

    @Basic
    @Column(name = "credit_limit")
    public float getLimit() {
        return limit;
    }
    public void setLimit(float limit) {
        this.limit = limit;
    }

    @OneToOne
    @JoinColumn(name = CURRENCY)
    public Currency getCurrency() {
        return currency;
    }
    public void setCurrency(Currency currency) {
        this.currency = currency;
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
    @Column(name = CREATED)
    public Timestamp getCreate() {
        return create;
    }
    public void setCreate(Timestamp create) {
        this.create = create;
    }

    @Basic
    @Column(name = "is_public")
    public boolean isPublic() {
        return isPublic;
    }
    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put(TITLE, title);
        object.put(LIMIT, limit);
        object.put(AMOUNT, budgetSum);
        object.put(TYPE, accountType.toString());
        object.put(CURRENCY, currency.shortJson());

        return object;
    }
}
