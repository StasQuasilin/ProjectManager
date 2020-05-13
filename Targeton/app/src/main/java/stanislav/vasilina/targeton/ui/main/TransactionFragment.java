package stanislav.vasilina.targeton.ui.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import stanislav.vasilina.targeton.R;
import stanislav.vasilina.targeton.utils.subscribes.Subscribe;
import stanislav.vasilina.targeton.utils.subscribes.Subscriber;

public class TransactionFragment extends Fragment {

    private Subscriber subscriber = Subscriber.getInstance();

    public static Fragment newInstance(){
        return new TransactionFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                return false;
            }
        });

        subscriber.subscribe(Subscribe.transaction, handler);
    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getContext(), "TransactionFragment resume", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        Toast.makeText(getContext(), "TransactionFragment pause", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        subscriber.unSubscribe(Subscribe.transaction);
        Toast.makeText(getContext(), "TransactionFragment stop", Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.transactions_fragment, container, false);
    }
}
