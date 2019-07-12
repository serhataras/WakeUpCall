package com.groupnamenotfoundexception.wakeupcall.app.services;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import com.groupnamenotfoundexception.wakeupcall.app.alarm.AdvancedAlarm;

/**
 * Created by Caner on 22/07/2015.
 */
public class WakingStateChecker extends Service{
    private MyBinder binder = new MyBinder();
    private final double SHALLOW_SLEEP_CONSTANT = 0.6;

    private int SHALLOW_SLEEP_DURATION = 1;
    private int count = 0;

    private AsleepModeDataCollector dataCollector;

    double shallowSleepThreshold;
    boolean isBound = false;

    private int alarmIndex;

    final Handler handler = new Handler();
    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Log.i("STAT CHECKER","ALLAMA ALLAMA");
            if(checkSleepStage())
                count++;
            else
                count = 0;

            if( count > SHALLOW_SLEEP_DURATION){
                dataCollector.stop();
                handler.removeCallbacks(runnable);

                Log.i("STAT CHECKER","ITS FINISHED");

                Intent intent = new Intent(WakingStateChecker.this, AdvancedAlarm.class);
                intent.putExtra("Alarm Index",alarmIndex);
                startService(intent);
                unbindService(serviceConnection);
                stopSelf();
            }else{
                handler.postDelayed(runnable,1000);
            }
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("Waking","State");
        alarmIndex = intent.getExtras().getInt("Alarm Index");

        Intent i = new Intent(this,AsleepModeDataCollector.class);
        //startService(i);
        bindService(i, serviceConnection, Context.BIND_ABOVE_CLIENT);

        return super.onStartCommand(intent, flags, startId);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            AsleepModeDataCollector.MyBinder binder = (AsleepModeDataCollector.MyBinder) service;
            dataCollector = binder.getService();
            Log.i("BINDER",String.valueOf(binder.getService()));
            calibrate();
            handler.postDelayed(runnable,1500);
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class MyBinder extends Binder {
        public WakingStateChecker getService(){
            return WakingStateChecker.this;
        }
    }

    public boolean checkSleepStage(){
        return Math.sqrt(
                20 * Math.log10(dataCollector.getCollectionSound().get(dataCollector.latestKey)) * dataCollector.getCollectionMovement().get(dataCollector.latestKey)
        ) > shallowSleepThreshold;
    }

    public void calibrate(){
        shallowSleepThreshold = Math.sqrt(
                dataCollector.average(dataCollector.getCollectionMovement()) * 20 * Math.log10(dataCollector.average(dataCollector.getCollectionSound()) * SHALLOW_SLEEP_CONSTANT)
        );
    }
}
