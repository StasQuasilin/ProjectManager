package entity.finance.category;

import constants.Keys;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.persistence.*;

@Entity
@Table(name = "headers")
public class Header extends AbstractTitle {

    private Header parent;

    @Id
    @Override
    public int getId() {
        return super.getId();
    }

    @OneToOne
    @JoinColumn(name = "_parent")
    public Header getParent() {
        return parent;
    }
    public void setParent(Header parent) {
        this.parent = parent;
    }

    @Override
    public JSONObject toJson() {
        final JSONObject jsonObject = super.toJson();
        jsonObject.put(Keys.HEADER, getId());
        jsonObject.put(Keys.PATH, buildPath());
        return jsonObject;
    }

    private JSONArray buildPath() {
        JSONArray array = new JSONArray();
        if(parent != null){
            array.addAll(parent.buildPath());
            array.add(parent.toJson());
        }
        return array;
    }

    @Transient
    public String getTitle() {
        return getValue();
    }
}
