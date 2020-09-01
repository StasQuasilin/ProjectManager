package ua.svasilina.targeton.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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
import ua.svasilina.targeton.adapters.SearchListAdapter;
import ua.svasilina.targeton.dialogs.transactions.OnChangeListener;
import ua.svasilina.targeton.dialogs.transactions.SearchDialogItemBuilder;
import ua.svasilina.targeton.utils.TWatcher;
import ua.svasilina.targeton.utils.connection.Connector;

import static ua.svasilina.targeton.utils.constants.Keys.KEY;
import static ua.svasilina.targeton.utils.constants.Keys.RESULT;
import static ua.svasilina.targeton.utils.constants.Keys.STATUS;
import static ua.svasilina.targeton.utils.constants.Keys.SUCCESS;

public class SearchDialog<T> extends DialogFragment {

    private final LayoutInflater inflater;
    private final String searchApi;
    private final Connector connector = Connector.getInstance();
    private final SearchListAdapter<T> adapter;
    private final OnChangeListener<T> changeListener;

    public SearchDialog(Context context, LayoutInflater inflater, String searchApi,
                        final SearchDialogItemBuilder<T> builder,
                        OnChangeListener<T> changeListener,
                        SearchListBuilder<T> listBuilder) {
        this.inflater = inflater;
        this.searchApi = searchApi;
        this.changeListener = changeListener;
        adapter  = new SearchListAdapter<T>(context, R.layout.search_list_view, inflater, listBuilder) {
            @Override
            public void addItem(JSONObject jsonObject) {
                adapter.add(builder.create(jsonObject));
            }
        };
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View view = inflater.inflate(R.layout.search_dialog, null);

        final EditText textInput = view.findViewById(R.id.searchValue);
        textInput.addTextChangedListener(new TWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 2) {
                    find(s);
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


        builder.setView(view);
        builder.setNegativeButton(R.string.cancel, null);

        return builder.create();
    }

    private void find(CharSequence key) {
        adapter.clear();
        System.out.println("FIND: " + key);
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
                                for (int i = 0; i < result.length(); i++){
                                    adapter.addItem(result.getJSONObject(i));
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
                        System.out.println(error.getMessage());
                    }
                });
    }
}
