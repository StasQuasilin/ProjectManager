package ua.svasilina.targeton.dialogs.buys;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import ua.svasilina.targeton.R;
import ua.svasilina.targeton.entity.buys.BuyListItem;
import ua.svasilina.targeton.utils.OnSave;
import ua.svasilina.targeton.utils.connection.Connector;
import ua.svasilina.targeton.utils.constants.API;

import static ua.svasilina.targeton.utils.constants.Keys.COUNT;
import static ua.svasilina.targeton.utils.constants.Keys.ID;
import static ua.svasilina.targeton.utils.constants.Keys.LIST;
import static ua.svasilina.targeton.utils.constants.Keys.PRICE;
import static ua.svasilina.targeton.utils.constants.Keys.RESULT;
import static ua.svasilina.targeton.utils.constants.Keys.STATUS;
import static ua.svasilina.targeton.utils.constants.Keys.SUCCESS;
import static ua.svasilina.targeton.utils.constants.Keys.TITLE;

public class BuyListItemEditDialog extends DialogFragment {

    private final LayoutInflater inflater;
    private final Context context;
    private final BuyListItem item;
    private EditText itemName;
    private Button dateButton;
    private EditText buyCount;
    private EditText buyPrice;
    private boolean save;
    private final int buyListId;

    public BuyListItemEditDialog(Context context, BuyListItem item, int buyListId, OnSave onSave){
        this.context = context;
        this.item = item;
        this.buyListId = buyListId;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View view = inflater.inflate(R.layout.buy_list_item_edit, null);
        itemName = view.findViewById(R.id.buyListItemName);
        dateButton = view.findViewById(R.id.buyDateButton);
        buyCount = view.findViewById(R.id.buyCount);
        buyPrice = view.findViewById(R.id.buyPrice);
        init();

        builder.setView(view);
        builder.setTitle(context.getResources().getString(R.string.buy_list_item_edit));
        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                save();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancel();
            }
        });
        return builder.create();
    }

    private void cancel() {
        if (!save){
            dismiss();
        }
    }

    private void init() {
        itemName.setText(item.getName());
        buyCount.setText(String.valueOf(item.getCount()));
        buyPrice.setText(String.valueOf(item.getPrice()));
    }


    private void save() {
        if (!save) {
            save = true;
            item.setName(itemName.getText().toString());
            int count = parseInt(buyCount);
            int price = parseInt(buyPrice);
            item.setCount(count);
            item.setPrice(price);

            if (buyListId != -1){
                final HashMap<String, Object> args = new HashMap<>();
                args.put(ID, item.getId());
                args.put(LIST, buyListId);
                args.put(TITLE, item.getName());
                args.put(COUNT, count);
                args.put(PRICE, price);

                Connector connector = new Connector();
                connector.newJsonReq(context, API.SAVE_BUY_LIST_ITEM, new JSONObject(args), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            final String status = response.getString(STATUS);
                            if (status.equals(SUCCESS)){
                                final int result = response.getInt(RESULT);
                                item.setId(result);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
            }

            cancel();
        }
    }

    private int parseInt(EditText editText) {
        final String text = editText.getText().toString();
        if (text.length() > 0){
            return Integer.parseInt(text);
        }
        return 0;
    }
}
