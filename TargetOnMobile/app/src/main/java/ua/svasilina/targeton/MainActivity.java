package ua.svasilina.targeton;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import ua.svasilina.targeton.ui.login.LoginActivity;
import ua.svasilina.targeton.ui.main.ApplicationFragment;
import ua.svasilina.targeton.ui.main.CalendarFragment;
import ua.svasilina.targeton.ui.main.GoalsFragment;
import ua.svasilina.targeton.ui.main.transactions.TransactionFragment;
import ua.svasilina.targeton.utils.background.BackgroundWorkerUtil;
import ua.svasilina.targeton.utils.storage.UserAccessStorage;

public class MainActivity extends AppCompatActivity {

    ActionBar actionBar;
    private TransactionFragment transactionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        actionBar = getSupportActionBar();
        final Context context = getApplicationContext();
        final LayoutInflater inflater = getLayoutInflater();

        transactionFragment = new TransactionFragment(context, inflater);
        if (savedInstanceState == null) {
            setView(transactionFragment);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.goals:
                setView(GoalsFragment.newInstance());
                return true;
            case R.id.transactions:
                setView(transactionFragment);
                return true;
            case R.id.calendar:
                    setView(CalendarFragment.getInstance());
                    return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setView(ApplicationFragment fragment) {
        final String userAccess = UserAccessStorage.getUserAccess(getApplicationContext());
        if (userAccess == null){
            final Context context = getApplicationContext();
            final Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
        } else {
            actionBar.setTitle(fragment.getTitle());
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commitNow();
        }
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        BackgroundWorkerUtil.getInstance().stopWorker(getApplicationContext());
    }

    @Override
    protected void onPause() {
        super.onPause();
        BackgroundWorkerUtil.getInstance().runWorker(getApplicationContext());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BackgroundWorkerUtil.getInstance().runWorker(getApplicationContext());
    }
}
