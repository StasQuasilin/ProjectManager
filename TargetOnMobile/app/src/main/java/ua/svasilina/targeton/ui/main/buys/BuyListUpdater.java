package ua.svasilina.targeton.ui.main.buys;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import ua.svasilina.targeton.adapters.buys.ListViewAdapter;
import ua.svasilina.targeton.entity.buys.BuyList;
import ua.svasilina.targeton.utils.subscribes.updaters.DataUpdater;

import static ua.svasilina.targeton.utils.constants.Keys.ID;

public class BuyListUpdater implements DataUpdater {

    private final ListViewAdapter listViewAdapter;
    private final HashMap<Integer, BuyList> hashMap = new HashMap<>();
    public BuyListUpdater(ListViewAdapter listViewAdapter) {
        this.listViewAdapter = listViewAdapter;
    }

    @Override
    public void update(JSONObject object) throws JSONException {
        final int id = object.getInt(ID);
        BuyList buyList = hashMap.get(id);
        if(buyList == null){
            buyList = new BuyList(object);
            hashMap.put(id, buyList);
            listViewAdapter.add(buyList);
        }else {
            buyList.updateValues(object);
        }
    }

    @Override
    public void sort() {

    }
}
