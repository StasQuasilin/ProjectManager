package ua.svasilina.targeton.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import ua.svasilina.targeton.R;
import ua.svasilina.targeton.TransactionAddActivity;

public class AccountsWidget extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int id : appWidgetIds){
            updateWidget(context, appWidgetManager, id);
        }
    }

    private void updateWidget(Context context, AppWidgetManager appWidgetManager, int id) {
        RemoteViews widgetViews = new RemoteViews(context.getPackageName(), R.layout.accounts_widget);
        Intent intent = new Intent(context, TransactionAddActivity.class);
//        intent.setAction(TRANSACTION_ADD);
        widgetViews.setOnClickPendingIntent(R.id.transactionAdd, PendingIntent.getActivity(context, 0, intent, 0));
        appWidgetManager.updateAppWidget(id, widgetViews);
    }
}
