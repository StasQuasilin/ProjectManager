package ua.svasilina.targeton.dialogs;

import android.view.View;

public interface SearchListBuilder<T> {
    void build(T item, View view);
}
