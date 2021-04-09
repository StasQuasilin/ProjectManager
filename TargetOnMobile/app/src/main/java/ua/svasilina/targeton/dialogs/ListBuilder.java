package ua.svasilina.targeton.dialogs;

import android.view.View;

public interface ListBuilder<T> {
    void build(T item, View view);
}
