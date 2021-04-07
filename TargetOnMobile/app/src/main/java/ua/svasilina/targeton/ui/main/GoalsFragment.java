package ua.svasilina.targeton.ui.main;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

import ua.svasilina.targeton.R;
import ua.svasilina.targeton.adapters.SimpleListAdapter;
import ua.svasilina.targeton.dialogs.SearchListBuilder;
import ua.svasilina.targeton.dialogs.transactions.SearchDialogItemBuilder;
import ua.svasilina.targeton.entity.Goal;
import ua.svasilina.targeton.entity.TaskStatistic;
import ua.svasilina.targeton.utils.builders.DateTimeBuilder;
import ua.svasilina.targeton.utils.constants.Constants;
import ua.svasilina.targeton.utils.constants.Keys;
import ua.svasilina.targeton.utils.subscribes.DataHandler;
import ua.svasilina.targeton.utils.subscribes.Subscribe;
import ua.svasilina.targeton.utils.subscribes.Subscriber;
import ua.svasilina.targeton.utils.subscribes.updaters.DataUpdater;

public class GoalsFragment extends ApplicationFragment implements DataUpdater {

    private final Context context;
    private final Subscriber subscriber = Subscriber.getInstance();
    private final DataHandler handler;
    private final HashMap<Integer, Goal> goalHashMap = new HashMap<>();
    private SimpleListAdapter<Goal> adapter;
    private DateTimeBuilder dateTimeBuilder;


    public GoalsFragment(Context context) {
        this.context = context;
        handler = new DataHandler(this);
        dateTimeBuilder = new DateTimeBuilder(Constants.DATE_PATTERN);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.goals_fragment, container, false);
        adapter = new SimpleListAdapter<>(context, R.layout.goal_view, inflater, new SearchListBuilder<Goal>() {
            @Override
            public void build(Goal item, View view) {
                final TextView goalTitleView = view.findViewById(R.id.goalTitle);
                goalTitleView.setText(item.getTitle());

                final View dateBeginGroup = view.findViewById(R.id.dateBeginGroup);
                final TextView dateBeginView = dateBeginGroup.findViewById(R.id.dateBegin);
                final Calendar begin = item.getBegin();
                if (begin != null){
                    dateBeginGroup.setVisibility(View.VISIBLE);
                    dateBeginView.setText(dateTimeBuilder.build(begin));
                } else {
                    dateBeginGroup.setVisibility(View.GONE);
                }
                final View dateEndGroup = view.findViewById(R.id.dateEndGroup);
                final TextView dateEndView = dateEndGroup.findViewById(R.id.dateEnd);
                final Calendar end = item.getEnd();
                if (end != null){
                    dateEndGroup.setVisibility(View.VISIBLE);
                    dateEndView.setText(dateTimeBuilder.build(end));
                } else {
                    dateEndGroup.setVisibility(View.GONE);
                }
                final ProgressBar dateProgressBar = view.findViewById(R.id.dateProgressBar);

                if (begin != null && end != null){
                    final Calendar now = Calendar.getInstance();
                    final long l1 = now.getTimeInMillis() - begin.getTimeInMillis();
                    final long l2 = end.getTimeInMillis() - begin.getTimeInMillis();
                    dateProgressBar.setMax(1000);
                    dateProgressBar.setProgress((int) (1f * l1 / l2 * 1000));
                    dateProgressBar.setVisibility(View.VISIBLE);
                } else {
                    dateProgressBar.setVisibility(View.GONE);
                }

                final TaskStatistic statistic = item.getStatistic();
                final View statisticView = view.findViewById(R.id.statisticContent);
                if(statistic != null){
                    int active = statistic.getActive();
                    final int progressing = statistic.getProgressing();
                    final int done = statistic.getDone();
                    final int other = statistic.getOther();

                    ProgressBar progressBar = statisticView.findViewById(R.id.progressProgressBar);
                    progressBar.setMax(active + progressing + done + other);
                    progressBar.setProgress(done);

                    final TextView activeTaskView = statisticView.findViewById(R.id.activeTask);
                    activeTaskView.setText(String.valueOf(active));

                    final TextView progressingTaskView = statisticView.findViewById(R.id.progressingTask);
                    progressingTaskView.setText(String.valueOf(progressing));

                    final TextView doneTaskView = statisticView.findViewById(R.id.doneTask);
                    doneTaskView.setText(String.valueOf(done));

                    final TextView otherTaskView = statisticView.findViewById(R.id.otherTask);
                    otherTaskView.setText(String.valueOf(other));

                } else {
                    statisticView.setVisibility(View.GONE);
                }

            }
        }, new SearchDialogItemBuilder<Goal>() {
            @Override
            public Goal create(JSONObject json) {
                return new Goal(json);
            }
        });

        final ListView goalList = view.findViewById(R.id.goalList);
        goalList.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        subscriber.subscribe(Subscribe.goal, handler, context);
    }

    @Override
    public void onPause() {
        super.onPause();
        subscriber.unsubscribe(Subscribe.goal);
    }

    @Override
    public String getTitle() {
        return context.getResources().getString(R.string.goals_title);
    }

    @Override
    public void update(JSONObject object) {
        try {
            final int id = object.getInt(Keys.ID);
            Goal goal = goalHashMap.get(id);
            if (goal == null){
                goal = new Goal(object);
                goalHashMap.put(goal.getId(), goal);
                adapter.add(goal);
            } else {
                goal.update(object);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void sort() {

    }
}
