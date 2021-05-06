package ua.svasilina.targeton.adapters.buys;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import ua.svasilina.targeton.adapters.SimpleListAdapter;
import ua.svasilina.targeton.dialogs.ListBuilder;
import ua.svasilina.targeton.dialogs.transactions.ItemBuilder;
import ua.svasilina.targeton.entity.buys.BuyList;

public class ListViewAdapter extends SimpleListAdapter<BuyList> {

    public ListViewAdapter(@NonNull @NotNull Context context, int resource, LayoutInflater inflater, ListBuilder<BuyList> listBuilder, ItemBuilder<BuyList> itemBuilder) {
        super(context, resource, inflater, listBuilder, itemBuilder);
    }
}
