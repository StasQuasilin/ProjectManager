package ua.svasilina.targeton.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import ua.svasilina.targeton.R;
import ua.svasilina.targeton.adapters.SimpleListAdapter;
import ua.svasilina.targeton.dialogs.transactions.OnChangeListener;
import ua.svasilina.targeton.dialogs.transactions.SearchDialogItemBuilder;
import ua.svasilina.targeton.utils.TWatcher;
import ua.svasilina.targeton.utils.connection.Connector;

import static ua.svasilina.targeton.utils.constants.Keys.ID;
import static ua.svasilina.targeton.utils.constants.Keys.KEY;
import static ua.svasilina.targeton.utils.constants.Keys.RESULT;
import static ua.svasilina.targeton.utils.constants.Keys.STATUS;
import static ua.svasilina.targeton.utils.constants.Keys.SUCCESS;
import static ua.svasilina.targeton.utils.constants.Keys.TITLE;

public class SearchDialog<T> extends DialogFragment {

    private final LayoutInflater inflater;
    private final String searchApi;
    private final Connector connector = Connector.getInstance();
    private final SimpleListAdapter<T> adapter;
    private final OnChangeListener<T> changeListener;
    private final SearchDialogItemBuilder<T> itemBuilder;
    private EditText textInput;
    private SearchTimer<T> timer;
    private String title;
    private AlertDialog.Builder builder;

    public SearchDialog(Context context, LayoutInflater inflater, String searchApi,
                        final SearchDialogItemBuilder<T> itemBuilder,
                        OnChangeListener<T> changeListener,
                        SearchListBuilder<T> listBuilder, String title) {
        this.inflater = inflater;
        this.searchApi = searchApi;
        this.changeListener = changeListener;
        this.itemBuilder = itemBuilder;
        adapter  = new SimpleListAdapter<T>(context, R.layout.search_list_view, inflater, listBuilder, itemBuilder) {
            @Override
            public void addItem(JSONObject jsonObject) {
                adapter.add(itemBuilder.create(jsonObject));
            }
        };
        timer = new SearchTimer<>(300, 1, this);
        this.title = title;
    }

    private Button addCategoryButton;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        builder = new AlertDialog.Builder(getActivity());
        final View view = inflater.inflate(R.layout.search_dialog, null);
        addCategoryButton = view.findViewById(R.id.addCategoryButton);
        addCategoryButton.setVisibility(View.GONE);
        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final HashMap<String, Object> map = new HashMap<>();
                map.put(ID, -1);
                map.put(TITLE, textInput.getText().toString());
                changeListener.onChange(itemBuilder.create(new JSONObject(map)));
                dismiss();
            }
        });

        textInput = view.findViewById(R.id.searchValue);
        textInput.addTextChangedListener(new TWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                timer.cancel();
                addCategoryButton.setVisibility(View.GONE);
                if (s.length() > 1) {
                    timer.start();
                }
            }
        });
        final ListView list = view.findViewById(R.id.itemList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final T item = adapter.getItem(position);
                if (item != null){
                    changeListener.onChange(item);
                    dismiss();
                }
            }
        });

        builder.setTitle(title);
        builder.setView(view);
        builder.setNegativeButton(R.string.cancel, null);

        return builder.create();
    }

    private void find() {
        final String s = textInput.getText().toString();
        find(s);
    }

    private void find(CharSequence key) {
        adapter.clear();

        HashMap<String, CharSequence> param = new HashMap<>();
        param.put(KEY, key.toString());
        connector.newJsonReq(
                getContext(),
                searchApi,
                new JSONObject(param),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            final String status = response.getString(STATUS);
                            if (status.equals(SUCCESS)){
                                final JSONArray result = response.getJSONArray(RESULT);
                                if (result.length() > 0){
                                    for (int i = 0; i < result.length(); i++){
                                        adapter.addItem(result.getJSONObject(i));
                                    }
                                } else {
                                    addCategoryButton.setVisibility(View.VISIBLE);
                                    builder.setNeutralButton(R.string.add_category, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            System.out.println("New category");
                                        }
                                    });
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        System.out.println("!" + error.getMessage());
                    }
                });
    }

    private static class SearchTimer<T> extends CountDownTimer {

        private SearchDialog<T> searchDialog;

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public SearchTimer(long millisInFuture, long countDownInterval, SearchDialog<T> tSearchDialog) {
            super(millisInFuture, countDownInterval);
            this.searchDialog = tSearchDialog;
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            searchDialog.find();
        }
    }
}
