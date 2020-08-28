package ua.svasilina.targeton.ui.main.transactions;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import ua.svasilina.targeton.R;

public class TransactionEdit extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_edit);
    }
}
