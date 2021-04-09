package ua.svasilina.targeton.ui.main.tree;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;

import ua.svasilina.targeton.R;
import ua.svasilina.targeton.adapters.SimpleListAdapter;
import ua.svasilina.targeton.dialogs.ListBuilder;
import ua.svasilina.targeton.dialogs.transactions.ItemBuilder;
import ua.svasilina.targeton.entity.goal.SimpleGoal;
import ua.svasilina.targeton.entity.tree.SimpleTask;
import ua.svasilina.targeton.entity.tree.Task;
import ua.svasilina.targeton.entity.tree.TaskStatus;
import ua.svasilina.targeton.utils.connection.Connector;
import ua.svasilina.targeton.utils.constants.API;
import ua.svasilina.targeton.utils.constants.Keys;
import ua.svasilina.targeton.utils.subscribes.updaters.DataUpdater;

import static ua.svasilina.targeton.utils.constants.Keys.CHILDREN;

public class GoalTreeHandler implements DataUpdater {

    SimpleListAdapter<SimpleGoal> adapter;
    SimpleListAdapter<SimpleTask> pathAdapter;
    SimpleListAdapter<Task> taskAdapter;
    private final LinkedList<SimpleTask> path = new LinkedList<>();
    private final Connector connector = Connector.getInstance();
    private final Context context;

    public GoalTreeHandler(final Context context) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        adapter = new SimpleListAdapter<>(context, android.R.layout.simple_dropdown_item_1line, inflater, new ListBuilder<SimpleGoal>() {
            @Override
            public void build(SimpleGoal item, View view) {
                final TextView textView = view.findViewById(android.R.id.text1);
                textView.setText(item.getTitle());
            }
        }, new ItemBuilder<SimpleGoal>() {
            @Override
            public SimpleGoal create(JSONObject json) {
                return null;
            }
        });
        pathAdapter = new SimpleListAdapter<>(context, R.layout.path_view, inflater, new ListBuilder<SimpleTask>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void build(SimpleTask item, View view) {
                final TextView taskNameView = view.findViewById(R.id.pathName);
                final int position = pathAdapter.getPosition(item);
                taskNameView.setText(position + ". " + item.getTitle());
                view.setPadding(position * 24, 0, 0, 0);
            }
        }, null);

        taskAdapter = new SimpleListAdapter<>(context, R.layout.task_view, inflater, new ListBuilder<Task>() {
            @Override
            public void build(Task item, View view) {
                final TextView taskNameView = view.findViewById(R.id.taskName);
                taskNameView.setText(item.getTitle());

                final TextView taskStatusView = view.findViewById(R.id.taskStatus);
                final TaskStatus status = item.getStatus();
                switch (status){
                    case done:
                        taskStatusView.setText(R.string.status_done);
                        break;
                    case progressing:
                        taskStatusView.setText(R.string.status_progressing);
                        break;
                }

                final ProgressBar progressBar = view.findViewById(R.id.progressBar);
                if (!item.isDoneIf()){
                    progressBar.setVisibility(View.INVISIBLE);
                }
                if (item.getChildrenCount() > 0){
                    view.setBackgroundColor(context.getResources().getColor(R.color.BLUE));
                }
                view.setPadding((pathAdapter.getCount() + 1) * 24, 0, 0, 0);
            }
        }, null);
    }

    @Override
    public void update(JSONObject object) {
        adapter.add(new SimpleGoal(object));
    }

    @Override
    public void sort() {

    }

    void getTreeItems(final int parent){
        final HashMap<String, Object> args = new HashMap<>();
        args.put(Keys.PARENT, parent);
        connector.newJsonReq(context, API.GET_TREE_ITEMS, new JSONObject(args), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                taskAdapter.clear();
//                if(pathAdapter.getCount() > 0){
//                    final Task item = pathAdapter.getItem(pathAdapter.getCount() - 1);
//                    if (item != null) {
//                        Task task = new Task(-1, item.getHeaderId(), "..");
//                        taskAdapter.add(task);
//                    }
//
//                }
                try {
                    final JSONArray jsonArray = response.getJSONArray(CHILDREN);

                    for (int i = 0; i < jsonArray.length(); i++){
                        Task task = new Task(jsonArray.getJSONObject(i));
                        taskAdapter.add(task);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
    }

    public void setList(Spinner goalList, ListView pathView, ListView itemListView) {
        goalList.setAdapter(adapter);
        goalList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final SimpleGoal item = adapter.getItem(position);
                if (item != null) {
                    pathAdapter.clear();
                    getTreeItems(item.getTitleId());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pathView.setAdapter(pathAdapter);
        pathView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final SimpleTask item = pathAdapter.getItem(position);
                if (item != null) {
                    final int count = pathAdapter.getCount();
                    for (int i = position + 1; i < count; i++){
                        pathAdapter.remove(pathAdapter.getItem(position + 1));
                    }
                    getTreeItems(item.getHeaderId());
                }
            }
        });

        itemListView.setAdapter(taskAdapter);
        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final SimpleTask item = taskAdapter.getItem(position);
                if (item != null) {
                    pathAdapter.add(item);
                    getTreeItems(item.getHeaderId());
                }
            }
        });
    }
}
