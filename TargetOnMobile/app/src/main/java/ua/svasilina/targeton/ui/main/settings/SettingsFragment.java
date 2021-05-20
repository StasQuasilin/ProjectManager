package ua.svasilina.targeton.ui.main.settings;

import android.content.Context;

import ua.svasilina.targeton.MainActivity;
import ua.svasilina.targeton.R;
import ua.svasilina.targeton.ui.main.ApplicationPage;

public class SettingsFragment extends ApplicationPage {

    private final Context context;

    public SettingsFragment(MainActivity mainActivity) {
        context = mainActivity.getApplicationContext();
    }

    @Override
    public String getTitle() {
        return context.getResources().getString(R.string.settings_title);
    }
}
