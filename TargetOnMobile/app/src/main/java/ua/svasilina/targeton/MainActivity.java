package ua.svasilina.targeton;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import ua.svasilina.targeton.ui.login.LoginActivity;
import ua.svasilina.targeton.ui.main.ApplicationFragment;
import ua.svasilina.targeton.ui.main.CalendarFragment;
import ua.svasilina.targeton.ui.main.GoalsFragment;
import ua.svasilina.targeton.ui.main.Pages;
import ua.svasilina.targeton.ui.main.accounts.AccountsFragment;
import ua.svasilina.targeton.ui.main.transactions.TransactionFragment;
import ua.svasilina.targeton.ui.main.tree.TreeFragment;
import ua.svasilina.targeton.utils.background.BackgroundWorkerUtil;
import ua.svasilina.targeton.utils.storage.UserAccessStorage;

public class MainActivity extends AppCompatActivity {

    ActionBar actionBar;
    private TransactionFragment transactionFragment;
    private AccountsFragment accountsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        actionBar = getSupportActionBar();
        final Context context = getApplicationContext();

        transactionFragment = new TransactionFragment(context);
        accountsFragment = new AccountsFragment(this);
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
                setView(new GoalsFragment(this));
                return true;
            case R.id.tree:
                openPage(Pages.tree, -1);

                break;
            case R.id.transactions:
                setView(transactionFragment = new TransactionFragment(getApplicationContext()));
                return true;
            case R.id.accounts:
                setView(accountsFragment = new AccountsFragment(this));
                return true;
            case R.id.calendar:
                setView(CalendarFragment.getInstance());
                return true;
            case R.id.exit:
                UserAccessStorage.saveUserAccess(getApplicationContext(), null);
                showLogin();
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setView(ApplicationFragment fragment) {
        final String userAccess = UserAccessStorage.getUserAccess(getApplicationContext());
        if (userAccess == null){
            showLogin();
        } else {
            actionBar.setTitle(fragment.getTitle());
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commitNow();
        }
    }

    void showLogin(){
        final Context context = getApplicationContext();
        final Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    private long backPressedTime;
    private Toast backToast;

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            finish();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), R.string.pressBack, Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();

    }

    @Override
    protected void onResume() {
        super.onResume();
        BackgroundWorkerUtil.getInstance().stopWorker(getApplicationContext());
    }

    @Override
    protected void onPause() {
        super.onPause();
//        BackgroundWorkerUtil.getInstance().runWorker(getApplicationContext());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        BackgroundWorkerUtil.getInstance().runWorker(getApplicationContext());
    }

    public void openPage(Pages page, int attr) {
        switch (page){
            case goal:
                break;
            case tree:
                setView(new TreeFragment(this, attr));
                break;
        }

    }
}
