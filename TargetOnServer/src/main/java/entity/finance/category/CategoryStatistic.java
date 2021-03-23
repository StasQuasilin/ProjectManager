package entity.finance.category;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "category_statistic")
public class CategoryStatistic implements Serializable {
    private int id;
    private Header header;
    private float plus;
    private float minus;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    @OneToOne
    @JoinColumn(name = "_category")
    public Header getHeader() {
        return header;
    }
    public void setHeader(Header header) {
        this.header = header;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryStatistic that = (CategoryStatistic) o;
        return header.getId() == that.header.getId();
    }

    @Override
    public int hashCode() {
        return header.getId();
    }
}
