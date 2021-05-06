package ua.svasilina.targeton.ui.main.buys;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import ua.svasilina.targeton.MainActivity;
import ua.svasilina.targeton.R;
import ua.svasilina.targeton.adapters.buys.ListViewAdapter;
import ua.svasilina.targeton.dialogs.ListBuilder;
import ua.svasilina.targeton.entity.buys.BuyList;
import ua.svasilina.targeton.ui.main.ApplicationPage;
import ua.svasilina.targeton.ui.main.Pages;
import ua.svasilina.targeton.utils.constants.Keys;
import ua.svasilina.targeton.utils.subscribes.DataHandler;
import ua.svasilina.targeton.utils.subscribes.Subscribe;
import ua.svasilina.targeton.utils.subscribes.Subscriber;

public class BuyListFragment extends ApplicationPage {

    private final MainActivity mainActivity;
    private final Context context;
    private final Subscriber subscriber = Subscriber.getInstance();
    private final DataHandler handler;
    private final ListViewAdapter listViewAdapter;

    public BuyListFragment(MainActivity mainActivity, int attr) {
        this.mainActivity = mainActivity;
        this.context = mainActivity.getApplicationContext();


        final LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listViewAdapter = new ListViewAdapter(context, R.layout.buy_list_view, li, new ListBuilder<BuyList>() {
            @Override
            public void build(final BuyList item, View view) {
                final TextView titleView = view.findViewById(R.id.buyListTitle);
                titleView.setText(item.getTitle());

                StringBuilder builder = new StringBuilder();
                final int cost = item.getCost();
                final int spend = item.getSpend();
                if(cost > 0 || spend > 0){
                    builder.append(spend).append(Keys.SPACE).append(Keys.SLASH).append(Keys.SPACE).append(cost);
                    builder.append(Keys.COMMA).append(Keys.SPACE);
                }
                final int items = item.getItems();
                builder.append(items).append(Keys.SPACE).append(Keys.ITEMS);

                final TextView detailsView = view.findViewById(R.id.buyListDetails);
                detailsView.setText(builder.toString());
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openList(item);
                    }
                });
            }
        }, null);
        handler = new DataHandler(new BuyListUpdater(listViewAdapter));
    }

    private void openList(BuyList buyList) {
        mainActivity.openPage(Pages.buys_edit, buyList.getId());
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.list_view, container, false);

        final ListView listView = view.findViewById(R.id.list);
        listView.setAdapter(listViewAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        subscriber.subscribe(Subscribe.buy, handler, context);
    }

    @Override
    public void onStop() {
        super.onStop();
        subscriber.unsubscribe(Subscribe.buy);
    }

    @Override
    public String getTitle() {
        return context.getResources().getString(R.string.buys);
    }
}
