package entity.finance.transactions;

import javax.persistence.Entity;

@Entity
public class TransactionDetails {
    private int id;
    private String name;
    private float amount;
}
