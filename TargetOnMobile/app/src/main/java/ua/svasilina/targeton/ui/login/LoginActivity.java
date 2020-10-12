package ua.svasilina.targeton.ui.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import ua.svasilina.targeton.MainActivity;
import ua.svasilina.targeton.R;
import ua.svasilina.targeton.utils.connection.Connector;
import ua.svasilina.targeton.utils.constants.API;
import ua.svasilina.targeton.utils.storage.UserAccessStorage;

import static ua.svasilina.targeton.utils.constants.Keys.EMAIL;
import static ua.svasilina.targeton.utils.constants.Keys.MESSAGE;
import static ua.svasilina.targeton.utils.constants.Keys.PASSWORD;
import static ua.svasilina.targeton.utils.constants.Keys.STATUS;
import static ua.svasilina.targeton.utils.constants.Keys.SUCCESS;
import static ua.svasilina.targeton.utils.constants.Keys.TOKEN;

public class LoginActivity extends Activity {

    private static final String TAG = "Login";
    private ProgressBar loginProgress;
    private EditText userNameInput;
    private EditText userPasswordInput;
    private Button loginButton;
    private TextView loginMessage;
    private static final int RC_GOOGLE_SIGN_IN = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        userNameInput = findViewById(R.id.userName);
        userPasswordInput = findViewById(R.id.userPassword);

        loginButton = findViewById(R.id.signInButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        SignInButton googleSignIn = findViewById(R.id.googleSignIn);
        googleSignIn.setSize(SignInButton.SIZE_WIDE);
        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
            }
        });

        loginProgress = findViewById(R.id.loginProgress);
        loginProgress.setVisibility(View.GONE);

        loginMessage = findViewById(R.id.loginMessage);

        final TextView registrationButton = findViewById(R.id.signUpButton);
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registration();
            }
        });
    }

    private void googleSignIn() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();

        final GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GOOGLE_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.

            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode() + " ( " + e.getCause() + " )" );
            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        if (account != null){
            System.out.println(account.getEmail());
            System.out.println(account.zab());
        }
    }

    private void login(){
        loginProgress.setVisibility(View.VISIBLE);
        loginButton.setEnabled(false);
        final String userName = userNameInput.getText().toString();
        final String userPassword = userPasswordInput.getText().toString();
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(EMAIL, userName);
            jsonObject.put(PASSWORD, Base64.encodeToString(userPassword.getBytes(), Base64.NO_WRAP));
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    API.SIGN_IN,
                    jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                final String status = response.getString(STATUS);
                                System.out.println(status);
                                if (status.equals(SUCCESS)){
                                    final String token = response.getString(TOKEN);
                                    final Context context = getApplicationContext();
                                    UserAccessStorage.saveUserAccess(context, token);
                                    context.startActivity(new Intent(context, MainActivity.class));

                                } else {
                                    final String message = response.getString(MESSAGE);
                                    showErrorMessage(message);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            showErrorMessage(error.getMessage());
                        }
                    }
            );
            Connector.getInstance().addRequest(getApplicationContext(), request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showErrorMessage(String message) {
        loginProgress.setVisibility(View.GONE);
        loginButton.setEnabled(true);
        loginMessage.setText(message);
    }

    private void registration(){
        final Context context = getApplicationContext();
        final Intent intent = new Intent(context, RegistrationActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {

    }
}
