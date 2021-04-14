package ua.svasilina.targeton.ui.main.transactions;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ua.svasilina.targeton.MainActivity;
import ua.svasilina.targeton.R;
import ua.svasilina.targeton.dialogs.transactions.TransactionEditInitializer;
import ua.svasilina.targeton.ui.main.ApplicationPage;

public class TransactionEditFragment extends ApplicationPage {

    private final MainActivity mainActivity;
    private final Context context;
    private final TransactionEditInitializer transactionEditInitializer;

    public TransactionEditFragment(MainActivity mainActivity, int attr) {
        this.mainActivity = mainActivity;
        context = mainActivity.getApplicationContext();

        transactionEditInitializer = new TransactionEditInitializer(getContext(), null, null, getFragmentManager());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.transaction_edit, null);
        transactionEditInitializer.initView(view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.transaction_menu_edit, menu);
    }

    @Override
    public String getTitle() {
        return context.getResources().getString(R.string.transactions_edit_title);
    }
}
