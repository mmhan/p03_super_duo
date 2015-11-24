package barqsoft.footballscores;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by mmhan on 24/11/15.
 */
public class BReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(BReceiver.class.getName(), "in onReceive of required class");
        if (intent.getAction().equals(WidgetUtils.WIDGET_UPDATE_ACTION)) {
//            remoteViewFactory.startLoading();
        }
    }
}
