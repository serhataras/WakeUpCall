package com.groupnamenotfoundexception.wakeupcall.app.managers;

import android.app.Activity;
import android.content.Context;
import com.groupnamenotfoundexception.wakeupcall.app.R;

/**
 * Created by Irma Chan on 26.7.2015.
 */
public class DailySleepManager {
    private long startOfSleep,endOfSleep,dailySleep;
    private Activity activity;

    public DailySleepManager(Activity activity) {
        this.activity = activity;
        startOfSleep = activity.getSharedPreferences(activity.getString(R.string.alarms_master),
                Context.MODE_PRIVATE).getLong(activity.getString(R.string.start_of_sleep), 0);
        endOfSleep = activity.getSharedPreferences(activity.getString(R.string.alarms_master),
                Context.MODE_PRIVATE).getLong(activity.getString(R.string.end_of_sleep), 0);
        dailySleep = endOfSleep-startOfSleep;
    }

    public long getDailySleep() {
        return dailySleep;
    }

    public long getStartOfSleep() {
        return startOfSleep;
    }

    public long getEndOfSleep() {
        return endOfSleep;
    }

    public int getSmileyFace(){
        if(dailySleep!=0&&dailySleep <= 6*60*1000)
            return -1;
        else if(dailySleep > 8*60*1000)
            return 1;
        else if(dailySleep!=0)
            return 0;
        return 404;
    }
}