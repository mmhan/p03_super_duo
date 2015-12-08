package barqsoft.footballscores;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
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

            appWidgetManager.updateAppWidget(appWidgetIds[i],remoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if(action.equals(WidgetUtils.WIDGET_UPDATE_ACTION)){
            Log.e(FootballWidgetProvider.class.getName(),"in onReceive of required class");
            final AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            final ComponentName cn = new ComponentName(context, FootballWidgetProvider.class);
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.listView);
        }
        super.onReceive(context, intent);
    }

        public static PendingIntent buildButtonPendingIntent(Context context) {
        // initiate widget update request
        Intent intent = new Intent(context,FootballWidgetProvider.class);
        intent.setAction(WidgetUtils.WIDGET_UPDATE_ACTION);
        return PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
//
//    public static void pushWidgetUpdate(Context context, RemoteViews remoteViews) {
//        ComponentName myWidget = new ComponentName(context,
//                FootballWidgetProvider.class);
//        AppWidgetManager manager = AppWidgetManager.getInstance(context);
//        manager.updateAppWidget(myWidget, remoteViews);
//    }
}
