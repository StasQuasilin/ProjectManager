package ua.svasilina.targeton.ui.main;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import ua.svasilina.targeton.ui.login.LoginActivity;

public abstract class ApplicationFragment extends Fragment {

    public abstract String getTitle();

    public void startActivity(Context context, Class<LoginActivity> activityClass){
        final Intent intent = new Intent(context, activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


}
