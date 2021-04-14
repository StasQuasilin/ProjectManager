package ua.svasilina.targeton.ui.main.tree;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONObject;

import ua.svasilina.targeton.MainActivity;
import ua.svasilina.targeton.R;
import ua.svasilina.targeton.ui.main.ApplicationPage;
import ua.svasilina.targeton.utils.subscribes.DataHandler;
import ua.svasilina.targeton.utils.subscribes.Subscribe;
import ua.svasilina.targeton.utils.subscribes.Subscriber;
import ua.svasilina.targeton.utils.subscribes.updaters.TreeUpdater;

public class TreeFragment extends ApplicationPage {

    private final Context context;
    private final Subscriber subscriber = Subscriber.getInstance();
    private final DataHandler goalHandler;

    private final DataHandler handler;
    private Spinner goalList;
    private GoalTreeHandler goalTreeHandler;

    public TreeFragment(MainActivity mainActivity, int goal) {
        this.context = mainActivity;
        goalTreeHandler = new GoalTreeHandler(context);
        goalHandler = new DataHandler(goalTreeHandler);
        handler = new DataHandler(new TreeUpdater(this));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tree_view, null);
        goalList = view.findViewById(R.id.goalSpinner);


        final ListView itemListView = view.findViewById(R.id.itemsList);
        final ListView pathView = view.findViewById(R.id.pathView);
        goalTreeHandler.setList(goalList, pathView, itemListView);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        subscribe();
    }

    private void subscribe() {
        subscriber.subscribe(Subscribe.goal, goalHandler, context);
        subscriber.subscribe(Subscribe.tree, handler, context);
    }

    @Override
    public String getTitle() {
        return context.getResources().getString(R.string.tree_title);
    }

    public void update(JSONObject object) {
        System.out.println(object);
    }
}
