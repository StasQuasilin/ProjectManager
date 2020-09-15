package ua.svasilina.targeton.utils.subscribes.updaters;

import org.json.JSONException;
import org.json.JSONObject;

import ua.svasilina.targeton.ui.main.transactions.TransactionFragment;

public class TransactionUpdater extends DataUpdater {

    private final TransactionFragment transactionFragment;

    public TransactionUpdater(TransactionFragment transactionFragment) {
        this.transactionFragment = transactionFragment;
    }

    @Override
    public void update(JSONObject object) {
        try {
            transactionFragment.update(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sort() {
        transactionFragment.sort();
    }
}
