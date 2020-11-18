package ua.svasilina.targeton.widgets;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import androidx.fragment.app.FragmentManager;

import ua.svasilina.targeton.MainActivity;
import ua.svasilina.targeton.R;
import ua.svasilina.targeton.TransactionAddActivity;
import ua.svasilina.targeton.receivers.TransactionAddReceiver;
import ua.svasilina.targeton.ui.main.transactions.TransactionFragment;

public class TransactionAddWidget extends AppWidgetProvider {


    private final static String TRANSACTION_ADD = "transactionAdd";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (TRANSACTION_ADD.equals(intent.getAction())){

        }

        System.out.println("RECEIVE: " + intent.getAction() + " - " + intent.getIntExtra("WIDGET_ID", -1));
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        for (int i : appWidgetIds){
            updateWidget(context, appWidgetManager, i);
        }
    }

    private void updateWidget(Context context, AppWidgetManager appWidgetManager, int widgetId) {
        RemoteViews widgetViews = new RemoteViews(context.getPackageName(), R.layout.transaction_add_widget);
        Intent intent = new Intent(context, TransactionAddActivity.class);
        intent.setAction(TRANSACTION_ADD);
        widgetViews.setOnClickPendingIntent(R.id.transactionAdd, PendingIntent.getActivity(context, 0, intent, 0));
        appWidgetManager.updateAppWidget(widgetId, widgetViews);
    }
}
