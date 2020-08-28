package ua.svasilina.targeton.utils.background;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;

import ua.svasilina.targeton.utils.storage.UserAccessStorage;

import static ua.svasilina.targeton.utils.constants.Keys.ACCESS;

public class BackgroundWorker extends Worker {

    public static final String TAG = "BW";
    private Handler handler = null;
    private String userAccess;

    public BackgroundWorker(@NonNull final Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        userAccess = UserAccessStorage.getUserAccess(context);
        if (userAccess != null) {
            handler = new Handler(Looper.getMainLooper()) {
                public void handleMessage(@NotNull Message message) {
                    Date currentTime = Calendar.getInstance().getTime();
                    final String string = message.getData().getString(ACCESS);
                    String msg = "Do work as" + string + " at " + currentTime;
                    Log.i(TAG, msg);
                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                }
            };
        } else {

            Log.w(TAG, "Stop background worker cause no user access");
            BackgroundWorkerUtil.getInstance().stopWorker(context);
        }
    }

    @NonNull
    @Override
    public Result doWork() {
        if (handler != null) {
            Bundle bundle = new Bundle();
            bundle.putString(ACCESS, userAccess);
            final Message message = new Message();
            message.setData(bundle);
            handler.sendMessage(message);
        }
        return Result.success();
    }
}
