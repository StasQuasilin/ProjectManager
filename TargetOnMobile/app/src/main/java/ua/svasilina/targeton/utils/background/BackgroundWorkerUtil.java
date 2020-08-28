package ua.svasilina.targeton.utils.background;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class BackgroundWorkerUtil {

    private static final int DURATION = 10;
    private boolean alreadyRun = false;

    private static BackgroundWorkerUtil instance;

    public static BackgroundWorkerUtil getInstance(){
        if (instance == null){
            instance = new BackgroundWorkerUtil();
        }
        return instance;
    }

    private BackgroundWorkerUtil() {}

    public void runWorker(final Context context){
        if (!alreadyRun) {
            alreadyRun = true;
            OneTimeWorkRequest one = new OneTimeWorkRequest.Builder(BackgroundWorker.class)
                    .addTag(BackgroundWorker.TAG)
                    .setInitialDelay(DURATION, TimeUnit.SECONDS)
                    .build();
            WorkManager.getInstance(context).enqueue(one);
        }
    }

    public void stopWorker(final Context context) {
        if (alreadyRun) {
            alreadyRun = false;
            Log.i("WorkManagerUtil", "Stop Worker " + BackgroundWorker.TAG);
            WorkManager.getInstance(context).cancelAllWorkByTag(BackgroundWorker.TAG);
        }
    }
}
