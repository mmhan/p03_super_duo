package barqsoft.footballscores;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class FootballWidgetProvider extends android.appwidget.AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {

        // initializing widget layout
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.widget_layout);

        // register for button event
        remoteViews.setOnClickPendingIntent(R.id.sync_button,
                buildButtonPendingIntent(context));

        // updating view with initial data
        remoteViews.setTextViewText(R.id.home_name, "Home Team");
        remoteViews.setTextViewText(R.id.away_name, "Away Team");
        remoteViews.setTextViewText(R.id.data_textview, "2015-11-18");
        remoteViews.setTextViewText(R.id.score_textview, "2-4");
        remoteViews.setImageViewResource(R.id.home_crest, R.drawable.arsenal);
        remoteViews.setImageViewResource(R.id.away_crest, R.drawable.chelsea);

        // request for widget update
        pushWidgetUpdate(context, remoteViews);
    }

    public static PendingIntent buildButtonPendingIntent(Context context) {
        ++MyWidgetIntentReceiver.clickCount;

        // initiate widget update request
        Intent intent = new Intent();
        intent.setAction(WidgetUtils.WIDGET_UPDATE_ACTION);
        return PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static CharSequence getDesc() {
        return "Sync to see some of our funniest joke collections";
    }

    private static CharSequence getTitle() {
        return "Funny Jokes";
    }

    public static void pushWidgetUpdate(Context context, RemoteViews remoteViews) {
        ComponentName myWidget = new ComponentName(context,
                FootballWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(myWidget, remoteViews);
    }
}
