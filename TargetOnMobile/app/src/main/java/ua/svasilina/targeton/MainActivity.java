package ua.svasilina.targeton;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import ua.svasilina.targeton.ui.login.LoginActivity;
import ua.svasilina.targeton.ui.main.ApplicationPage;
import ua.svasilina.targeton.ui.main.CalendarFragment;
import ua.svasilina.targeton.ui.main.GoalsFragment;
import ua.svasilina.targeton.ui.main.Pages;
import ua.svasilina.targeton.ui.main.accounts.AccountsFragment;
import ua.svasilina.targeton.ui.main.transactions.TransactionEditFragment;
import ua.svasilina.targeton.ui.main.transactions.TransactionFragment;
import ua.svasilina.targeton.ui.main.tree.TreeFragment;
import ua.svasilina.targeton.utils.background.BackgroundWorkerUtil;
import ua.svasilina.targeton.utils.storage.UserAccessStorage;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_goals)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
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
        TransactionFragment transactionFragment;
        switch (itemId){
            case R.id.goals:

                return true;
            case R.id.tree:
                openPage(Pages.tree, -1);

                break;
            case R.id.transactions:
                setView(transactionFragment = new TransactionFragment(this));
                return true;
            case R.id.accounts:
                setView(new AccountsFragment(this));
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

    private void setView(ApplicationPage fragment) {
        final String userAccess = UserAccessStorage.getUserAccess(getApplicationContext());
        if (userAccess == null){
            showLogin();
        } else {
            toolbar.setTitle(fragment.getTitle());
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
                setView(new GoalsFragment(this));
                break;
            case tree:
                setView(new TreeFragment(this, attr));
                break;
            case transactionEdit:
                setView(new TransactionEditFragment(this, attr));
                break;
        }

    }
}
