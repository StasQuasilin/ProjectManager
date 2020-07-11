package entity.finance;

import entity.user.User;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import static constants.Keys.ID;
import static constants.Keys.NAME;
import static constants.Keys.OWNER;

public class Counterparty extends JsonAble {
    private int id;
    private String name;
    private User owner;

    @Override
    public JSONObject shortJson() {
        JSONObject jsonObject = getJsonObject();
        jsonObject.put(ID, id);
        jsonObject.put(NAME, name);
        return jsonObject;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = shortJson();
        jsonObject.put(OWNER, owner.toJson());
        return jsonObject;
    }
}
