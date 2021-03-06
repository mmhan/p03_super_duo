package barqsoft.footballscores.service;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.R;
import barqsoft.footballscores.ScoreData;

/**
 * Created by mmhan on 24/11/15.
 */
public class StackRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory,
        Loader.OnLoadCompleteListener<Cursor> {
    Context mContext;
    ArrayList<ScoreData> arrayList;
    int mAppWidgetId;
    int CURSOR_LOADER = 0;
    Loader<Cursor> mLoader;
    public StackRemoteViewFactory(Context context, Intent intent) {
        mContext = context;
        arrayList = new ArrayList<>();
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }
    @Override
    public void onCreate() {
        startLoading();
    }
    public void startLoading() {
        String[] today = new String[1];
//        Date dateNow = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
        Date dateNow = new Date(System.currentTimeMillis());
        SimpleDateFormat mformat = new SimpleDateFormat("yyyy-MM-dd");
        today[0] = mformat.format(dateNow);
        mLoader = new CursorLoader(mContext, DatabaseContract.scores_table.buildScoreWithDate(),
                null, null, today, null);
        mLoader.registerListener(CURSOR_LOADER, this);
        mLoader.startLoading();
    }
    @Override
    public void onDataSetChanged() {
        if(mLoader != null){
            mLoader.reset();
            Thread thread = new Thread() {
                public void run() {
                    Looper.prepare();

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do Work
                            startLoading();
                            handler.removeCallbacks(this);
                            Looper.myLooper().quit();
                        }
                    }, 2000);

                    Looper.loop();
                }
            };
            thread.start();
        }
    }
    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(),
                R.layout.scores_widget_list_item);
        ScoreData scoreData = arrayList.get(position);
        rv.setTextViewText(R.id.home_name, scoreData.mHome);
        rv.setTextViewText(R.id.away_name, scoreData.mAway);
        rv.setTextViewText(R.id.score_textview, scoreData.mHomeGoals + " - " + scoreData.mAwayGoals);
        rv.setTextViewText(R.id.data_textview, scoreData.mMatchTime);
        rv.setImageViewResource(R.id.home_crest,scoreData.home_crest);
        rv.setImageViewResource(R.id.away_crest,scoreData.away_crest);
        return rv;
    }
    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public void onLoadComplete(Loader<Cursor> loader, Cursor cursor) {
        cursor.moveToFirst();
        arrayList.clear();
        while(!cursor.isAfterLast()){
            arrayList.add(new ScoreData(cursor));
            cursor.moveToNext();
        }
    }
}
