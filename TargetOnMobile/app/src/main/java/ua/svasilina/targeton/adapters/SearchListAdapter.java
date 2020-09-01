package ua.svasilina.targeton.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ua.svasilina.targeton.R;
import ua.svasilina.targeton.dialogs.SearchListBuilder;

public class SearchListAdapter <T> extends ArrayAdapter<T> {

    private final LayoutInflater inflater;
    private final int resource;
    private final SearchListBuilder<T> builder;

    public SearchListAdapter(@NonNull Context context, int resource, LayoutInflater inflater, SearchListBuilder<T> builder) {
        super(context, resource);
        this.inflater = inflater;
        this.resource = resource;
        this.builder = builder;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final View view = convertView == null ? inflater.inflate(resource, parent, false) : convertView;
        final T item = getItem(position);
        builder.build(item, view);
        return view;
    }
}
