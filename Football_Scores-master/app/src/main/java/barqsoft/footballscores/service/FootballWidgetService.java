package barqsoft.footballscores.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

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
}

