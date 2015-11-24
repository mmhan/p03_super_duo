package barqsoft.footballscores;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import barqsoft.footballscores.service.FootballWidgetService;

public class FootballWidgetProvider extends android.appwidget.AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {

        for(int i = 0; i < appWidgetIds.length; ++i) {
            Intent intent = new Intent(context, FootballWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            // initializing widget layout
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.widget_layout);
            remoteViews.setRemoteAdapter(appWidgetIds[i], R.id.listView, intent);
            remoteViews.setOnClickPendingIntent(R.id.sync_button,
                    buildButtonPendingIntent(context));

            // request for widget update
            pushWidgetUpdate(context, remoteViews);
        }


//        // register for button event

//        // updating view with initial data
//        remoteViews.setTextViewText(R.id.home_name, "Home Team");
//        remoteViews.setTextViewText(R.id.away_name, "Away Team");
//        remoteViews.setTextViewText(R.id.data_textview, "2015-11-18");
//        remoteViews.setTextViewText(R.id.score_textview, "2-4");
//        remoteViews.setImageViewResource(R.id.home_crest, R.drawable.arsenal);
//        remoteViews.setImageViewResource(R.id.away_crest, R.drawable.chelsea);
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    public static PendingIntent buildButtonPendingIntent(Context context) {
        // initiate widget update request
        Intent intent = new Intent();
        intent.setAction(WidgetUtils.WIDGET_UPDATE_ACTION);
        return PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static void pushWidgetUpdate(Context context, RemoteViews remoteViews) {
        ComponentName myWidget = new ComponentName(context,
                FootballWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(myWidget, remoteViews);
    }
}
