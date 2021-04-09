package ua.svasilina.targeton.dialogs.transactions;

import org.json.JSONObject;

public interface ItemBuilder<T>{
    T create(JSONObject json);
}
