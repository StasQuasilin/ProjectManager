package stanislav.vasilina.targeton;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import stanislav.vasilina.targeton.ui.main.CalendarFragment;
import stanislav.vasilina.targeton.ui.main.MainFragment;
import stanislav.vasilina.targeton.ui.main.TransactionFragment;
import stanislav.vasilina.targeton.utils.connection.SocketConnector;
import stanislav.vasilina.targeton.utils.constants.Constants;

public class MainActivity extends AppCompatActivity {

    TextView statusBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        statusBar = findViewById(R.id.statusBar);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
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
            case R.id.transactions:
                setView(TransactionFragment.newInstance());
                return true;
            case R.id.calendar:
                    setView(CalendarFragment.getInstance());
        }
        return super.onOptionsItemSelected(item);
    }

    private void setView(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commitNow();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}
