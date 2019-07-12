package com.groupnamenotfoundexception.wakeupcall.app.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.display.DisplayManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Display;
import com.groupnamenotfoundexception.wakeupcall.app.R;

/**
 * Created by Alchemistake on 23/07/15.
 */
public class BeforeSleepManager extends Service{
    private MyBinder binder = new MyBinder();

    final Handler handler = new Handler();
    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(screenCheck()){
                Log.i("ALARM:", "Screen off");
                currentNumberOfChecks++;
            }else{
                Log.i("ALARM:", "Screen on");
                sendNotification();
                currentNumberOfChecks = 0;
                check = false;
            }

            if(currentNumberOfChecks > NUMBER_OF_CHECKS){
                check = false;
                Intent i = new Intent(BeforeSleepManager.this,AsleepModeDataCollector.class);
                i.putExtra("Alarm Index", alarmIndex);
                startService(i);

                ComponentName asleepMode = new ComponentName(BeforeSleepManager.this, AsleepModeDataCollector.class);
                PackageManager pm = BeforeSleepManager.this.getPackageManager();
                pm.setComponentEnabledSetting(asleepMode,
                        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                        PackageManager.DONT_KILL_APP);

                getSharedPreferences(getString(R.string.alarms_master),Context.MODE_PRIVATE).edit().putLong(getString(R.string.start_of_sleep), System.currentTimeMillis());

                stopSelf();
            }

            if(check)
                handler.postDelayed(runnable,SCREEN_CHECK_DELAY);
        }
    };

    public final static int NUMBER_OF_CHECKS = 5;
    private final int SCREEN_CHECK_DELAY = 2000;

    public int currentNumberOfChecks;
    public boolean check;

    private NotificationManager mNotificationManager;

    private int alarmIndex;

    @Override
    public void onCreate() {
        super.onCreate();

        currentNumberOfChecks = 0;
        check = false;
        sendNotification();
        Log.i("BEFORE","SLEEP");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class MyBinder extends Binder {
        BeforeSleepManager getService(){
            return BeforeSleepManager.this;
        }
    }

    public boolean screenCheck(){
        DisplayManager dm = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
        for (Display display : dm.getDisplays()) {
            if (display.getState() == Display.STATE_OFF) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent.getExtras().getBoolean("START?",false)) {
            startChecking();
            mNotificationManager.cancel(5000);
        }else{
            sendNotification();
        }
        alarmIndex = intent.getExtras().getInt("Alarm Index");
        return super.onStartCommand(intent, flags, startId);
    }

    private void sendNotification() {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent i = new Intent(this,BeforeSleepManager.class);
        i.putExtra("START?", true);
        i.putExtra("Alarm Index", alarmIndex);
        PendingIntent pendingIntent = PendingIntent.getService(this,4000,i,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("WAKE UP CALL")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("You should sleep!"))
                        .setContentText("You should sleep!")
                        .addAction(R.drawable.notification_template_icon_bg,"I GO TO SLEEP",pendingIntent);

        mBuilder.setOngoing(true);
        mBuilder.setContentIntent(pendingIntent);
        mNotificationManager.notify(5000, mBuilder.build());
    }

    public void startChecking(){
        check = true;
        handler.postDelayed(runnable,5000);

        mNotificationManager.cancel(5000);
    }
}
