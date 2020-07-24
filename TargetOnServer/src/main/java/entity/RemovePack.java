package entity;

import org.json.simple.JSONObject;
import utils.json.JsonAble;
import static constants.Keys.ID;

public class RemovePack extends JsonAble {

    private final int id;

    public RemovePack(int id) {
        this.id = id;
    }

    @Override
    public JSONObject toJson() {
        final JSONObject jsonObject = getJsonObject();
        jsonObject.put(ID, id);
        return jsonObject;
    }
}
