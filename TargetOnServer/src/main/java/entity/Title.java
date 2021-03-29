package entity;

import entity.finance.category.AbstractTitle;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "headers")
public class Title extends AbstractTitle {
    @Id
    public int getId() {
        return super.getId();
    }
}
