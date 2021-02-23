package entity.finance.category;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "category_statistic")
public class CategoryStatistic implements Serializable {
    private Category category;
    private float plus;
    private float minus;

    @Id
    @OneToOne
    @JoinColumn(name = "_category")
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    @Basic
    @Column(name = "_plus")
    public float getPlus() {
        return plus;
    }
    public void setPlus(float plus) {
        this.plus = plus;
    }

    @Basic
    @Column(name = "_minus")
    public float getMinus() {
        return minus;
    }
    public void setMinus(float minus) {
        this.minus = minus;
    }
}
