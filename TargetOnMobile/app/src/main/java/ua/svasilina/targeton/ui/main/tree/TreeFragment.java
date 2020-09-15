package ua.svasilina.targeton.ui.main.tree;

import android.content.Context;

import org.json.JSONObject;

import ua.svasilina.targeton.R;
import ua.svasilina.targeton.ui.main.ApplicationFragment;
import ua.svasilina.targeton.utils.subscribes.DataHandler;
import ua.svasilina.targeton.utils.subscribes.Subscribe;
import ua.svasilina.targeton.utils.subscribes.Subscriber;
import ua.svasilina.targeton.utils.subscribes.updaters.TreeUpdater;

public class TreeFragment extends ApplicationFragment {

    private final Context context;
    private final Subscriber subscriber = Subscriber.getInstance();
    private final DataHandler handler;

    public TreeFragment(Context context) {
        this.context = context;
        handler = new DataHandler(new TreeUpdater(this));
    }

    @Override
    public void onResume() {
        super.onResume();
        subscriber.subscribe(Subscribe.tree, handler, context);

    }

    @Override
    public String getTitle() {
        return context.getResources().getString(R.string.tree_title);
    }

    public void update(JSONObject object) {

    }
}
