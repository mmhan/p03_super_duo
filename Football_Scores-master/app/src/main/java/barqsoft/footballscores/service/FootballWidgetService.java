package barqsoft.footballscores.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

import barqsoft.footballscores.WidgetUtils;

/**
 * Created by mmhan on 24/11/15.
 */
public class FootballWidgetService extends RemoteViewsService{
    StackRemoteViewFactory remoteViewFactory;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Intent service_start = new Intent(this.getApplicationContext(), myFetchService.class);
        this.getApplicationContext().startService(service_start);
        remoteViewFactory = new StackRemoteViewFactory(this.getApplicationContext(), intent);
        return remoteViewFactory;
    }

    public class Receiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(WidgetUtils.WIDGET_UPDATE_ACTION)) {
                Log.e(StackRemoteViewFactory.class.getName(), "in onReceive of required class");
                remoteViewFactory.startLoading();
            }
        }
    }
}

