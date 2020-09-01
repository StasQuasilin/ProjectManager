package ua.svasilina.targeton.dialogs.transactions;

import org.json.JSONObject;

public interface SearchDialogItemBuilder<T>{
    T create(JSONObject json);
}
