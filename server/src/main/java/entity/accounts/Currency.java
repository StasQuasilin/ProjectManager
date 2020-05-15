package entity.accounts;

import constants.TableNames;
import org.json.simple.JSONObject;
import utils.JsonAble;

import javax.persistence.*;

import static constants.Keys.*;

@Entity
@Table(name = TableNames.CURRENCYES)
public class Currency extends JsonAble {
    private String id;
    private String sign;
    private int code;
    private String name;

    @Id
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = SIGN)
    public String getSign() {
        return sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }

    @Basic
    @Column(name = CODE)
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }

    @Basic
    @Column(name = NAME)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public JSONObject shortJson() {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put(SIGN, sign);
        return object;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = shortJson();
        object.put(CODE, code);
        object.put(NAME, name);
        return object;
    }
}
