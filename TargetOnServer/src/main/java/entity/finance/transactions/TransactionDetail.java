package entity.finance.transactions;

import entity.finance.category.Category;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;
import static constants.Keys.*;

@Entity
@Table(name = "transaction_details")
public class TransactionDetail extends JsonAble {
    private int id;
    private Transaction transaction;
    private Category category;
    private float amount;
    private float price;
    private String comment;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "_transaction")
    public Transaction getTransaction() {
        return transaction;
    }
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @OneToOne
    @JoinColumn(name = "_category")
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    @Basic
    @Column(name = "_amount")
    public float getAmount() {
        return amount;
    }
    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "_price")
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    @Basic
    @Column(name = "_comment")
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public JSONObject toJson() {
        final JSONObject jsonObject = getJsonObject();
        jsonObject.put(ID, id);
        jsonObject.put(CATEGORY, category.getId());
//        jsonObject.put(TITLE, category.getTitle());
        jsonObject.put(AMOUNT, amount);
        jsonObject.put(PRICE, price);
        jsonObject.put(COMMENT, comment);
        return jsonObject;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == getClass() && obj.hashCode() == hashCode();
    }
}
