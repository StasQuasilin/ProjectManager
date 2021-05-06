package ua.svasilina.targeton.ui.main.buys;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import ua.svasilina.targeton.MainActivity;
import ua.svasilina.targeton.R;
import ua.svasilina.targeton.adapters.SimpleListAdapter;
import ua.svasilina.targeton.dialogs.ListBuilder;
import ua.svasilina.targeton.dialogs.buys.BuyListItemEditDialog;
import ua.svasilina.targeton.entity.buys.BuyListItem;
import ua.svasilina.targeton.entity.buys.FullBuyList;
import ua.svasilina.targeton.ui.main.ApplicationPage;
import ua.svasilina.targeton.ui.main.Pages;
import ua.svasilina.targeton.utils.OnSave;
import ua.svasilina.targeton.utils.connection.Connector;
import ua.svasilina.targeton.utils.constants.API;
import ua.svasilina.targeton.utils.constants.Keys;

import static ua.svasilina.targeton.utils.constants.Keys.EQUALS;
import static ua.svasilina.targeton.utils.constants.Keys.ID;
import static ua.svasilina.targeton.utils.constants.Keys.SPACE;
import static ua.svasilina.targeton.utils.constants.Keys.STATUS;
import static ua.svasilina.targeton.utils.constants.Keys.SUCCESS;
import static ua.svasilina.targeton.utils.constants.Keys.TIMES;

public class BuyListEditFragment extends ApplicationPage {
    private MainActivity mainActivity;
    private Context context;
    private EditText buyListName;
    private ListView itemList;
    private EditText newItemName;
    private ImageButton addItemButton;
    private final SimpleListAdapter<BuyListItem> adapter;
    private FullBuyList buyList;
    private final FragmentManager fragmentManager;

    public BuyListEditFragment(MainActivity mainActivity, int attr, FragmentManager fragmentManager) {
        this.mainActivity = mainActivity;
        this.context = mainActivity.getApplicationContext();
        this.fragmentManager = fragmentManager;
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        adapter = new SimpleListAdapter<>(context, R.layout.buy_list_item_view, inflater, new ListBuilder<BuyListItem>() {
            @Override
            public void build(final BuyListItem item, View view) {

                final ImageButton deleteButton = view.findViewById(R.id.deleteItemButton);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.remove(item);
                    }
                });

                final View clickZone = view.findViewById(R.id.itemClickZone);

                clickZone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editItem(item);
                    }
                });

                final TextView buyListItemNameView = clickZone.findViewById(R.id.buyListItemName);
                buyListItemNameView.setText(item.getName());

                final TextView buyListItemDetailView = clickZone.findViewById(R.id.buyListItemDetail);
                final int price = item.getPrice();
                final int count = item.getCount();
                if (price > 0){
                    String builder = price + SPACE + TIMES + SPACE +
                            count + SPACE + EQUALS + SPACE + count * price;
                    buyListItemDetailView.setText(builder);
                } else {
                    buyListItemDetailView.setVisibility(View.GONE);
                }
            }
        }, null);
        final HashMap<String, Object> args = new HashMap<>();
        args.put(ID, attr);
        Connector connector = new Connector();
        connector.newJsonReq(context, API.SAVE_BUY_LIST + API.DATA, new JSONObject(args), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    final String status = response.getString(STATUS);
                    if (status.equals(SUCCESS)){
                        final JSONObject list = response.getJSONObject(Keys.LIST);
                        buyList = new FullBuyList(list);
                        init();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void editItem(BuyListItem item) {
        new BuyListItemEditDialog(context, item, buyList.getId(), new OnSave<BuyListItem>() {
            @Override
            public void handle(BuyListItem item) {
                adapter.remove(item);
                adapter.add(item);
            }
        }).show(fragmentManager, null);
    }

    private void init() {
        buyListName.setText(buyList.getTitle());
        adapter.addAll(buyList.getItems());
    }

    private void save(){
        close();
    }

    private void close(){
        mainActivity.openPage(Pages.buys, -1);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.buy_list_edit, container, false);
        buyListName = view.findViewById(R.id.listName);
        itemList = view.findViewById(R.id.itemList);
        newItemName = view.findViewById(R.id.newItemName);
        addItemButton = view.findViewById(R.id.addItemButton);
        itemList.setAdapter(adapter);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewItem();
            }
        });
        setHasOptionsMenu(true);
        return view;
    }

    private void addNewItem() {
        final String name = newItemName.getText().toString();
        if(name.length() > 0){
            BuyListItem item = new BuyListItem();
            item.setName(name);
            adapter.add(item);
            newItemName.getText().clear();
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.edit_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        final int itemId = item.getItemId();
        if (itemId == R.id.save){
            save();
        } else if(itemId == R.id.close){
            close();
        }
        return false;
    }

    @Override
    public String getTitle() {
        return context.getResources().getString(R.string.buy_list_edit);
    }
}
