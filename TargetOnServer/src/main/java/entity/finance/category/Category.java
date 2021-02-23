package entity.finance.category;

import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;
import java.io.Serializable;

import static constants.Keys.ID;
import static constants.Keys.TITLE;

@Entity
@Table(name = "categories")
public class Category extends JsonAble implements Serializable {
    private int id;
    private Header header;
    private boolean hidden;

    @Id
    @Column(name = "_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
    @Column(name = "hidden")
    public boolean isHidden() {
        return hidden;
    }
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    @Override
    public JSONObject shortJson() {
        JSONObject jsonObject = getJsonObject();
        jsonObject.put(ID, id);
        jsonObject.put(TITLE, header.getTitle());
        return jsonObject;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = shortJson();
        return jsonObject;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return getClass().equals(obj.getClass()) && hashCode() == obj.hashCode();
    }
}
