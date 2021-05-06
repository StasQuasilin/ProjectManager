package entity.finance.category;

import javax.persistence.*;

@Entity
@Table(name = "system_category")
public class SystemCategory {
    private int id;
    private CategoryType type;
    private String language;
    private Header header;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "_type")
    public CategoryType getType() {
        return type;
    }
    public void setType(CategoryType type) {
        this.type = type;
    }

    @Basic
    @Column(name = "_language")
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }

    @OneToOne
    @JoinColumn(name = "_header")
    public Header getHeader() {
        return header;
    }
    public void setHeader(Header category) {
        this.header = category;
    }
}
