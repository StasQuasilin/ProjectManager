package ua.svasilina.targeton.ui.main.transactions;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import ua.svasilina.targeton.dialogs.transactions.TransactionEditDialog;
import ua.svasilina.targeton.entity.transactions.Transaction;
import ua.svasilina.targeton.entity.transactions.UserData;
import ua.svasilina.targeton.utils.storage.UserDataStorage;

public class TransactionAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = getApplicationContext();
        try {
            final UserData userData = new UserData(new JSONObject(UserDataStorage.getUserData(context)));
            TransactionEditDialog ted = new TransactionEditDialog(context, new Transaction(), getLayoutInflater(), userData);
            ted.show(getSupportFragmentManager(), "TED");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
