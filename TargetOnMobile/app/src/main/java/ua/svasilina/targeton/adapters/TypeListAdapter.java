package ua.svasilina.targeton.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ua.svasilina.targeton.R;
import ua.svasilina.targeton.entity.transactions.TransactionType;

public class TypeListAdapter extends ArrayAdapter<TransactionType> {

    private final LayoutInflater inflater;
    private final int resource;
    private TransactionType selected;

    public TypeListAdapter(@NonNull Context context, int resource, LayoutInflater inflater, TransactionType type) {
        super(context, resource);
        this.inflater = inflater;
        this.resource = resource;
        selected = type;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final View view = convertView == null ? inflater.inflate(resource, parent, false) : convertView;
        final TransactionType item = getItem(position);
        final RadioButton itemCheck = view.findViewById(R.id.itemRadio);

        if (item != null) {
            itemCheck.setChecked(selected == item);
            itemCheck.setText(item.toString());
        }
        return view;
    }

    public void select(int position) {
        selected = getItem(position);
        System.out.println("Selected: " + selected);
    }
}
