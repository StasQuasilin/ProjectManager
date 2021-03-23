package entity.finance.transactions;

import entity.finance.category.Header;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;
import static constants.Keys.*;

@Entity
@Table(name = "transaction_details")
public class TransactionDetail extends JsonAble {
    private int id;
    private Transaction transaction;
    private Header header;
    private float amount;
    private float price;
    private float rate;
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
    @JoinColumn(name = "_header")
    public Header getHeader() {
        return header;
    }
    public void setHeader(Header header) {
        this.header = header;
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
    @Column(name = "_rate")
    public float getRate() {
        return rate;
    }
    public void setRate(float rate) {
        this.rate = rate;
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
        jsonObject.put(HEADER, header.getId());
        jsonObject.put(TITLE, header.getTitle());
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

    @Transient
    public float getTotalPrice(){
        return amount * price;
    }
}
