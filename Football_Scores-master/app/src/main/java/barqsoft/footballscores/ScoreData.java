package barqsoft.footballscores;

import android.database.Cursor;

/**
 * Created by mmhan on 24/11/15.
 */
public class ScoreData {
    public String mHome, mAway, mHomeGoals, mAwayGoals, mDate, mLeague, mMatchDay, mId, mMatchTime;
    public ScoreData(Cursor cursor) {
        mHome = cursor.getString(scoresAdapter.COL_HOME);
        mAway = cursor.getString(scoresAdapter.COL_AWAY);
        mHomeGoals = cursor.getString(scoresAdapter.COL_HOME_GOALS);
        mAwayGoals = cursor.getString(scoresAdapter.COL_AWAY_GOALS);
        mDate = cursor.getString(scoresAdapter.COL_DATE);
        mLeague = cursor.getString(scoresAdapter.COL_LEAGUE);
        mMatchDay = cursor.getString(scoresAdapter.COL_MATCHDAY);
        mId = cursor.getString(scoresAdapter.COL_ID);
        mMatchTime = cursor.getString(scoresAdapter.COL_MATCHTIME);
    }
}
