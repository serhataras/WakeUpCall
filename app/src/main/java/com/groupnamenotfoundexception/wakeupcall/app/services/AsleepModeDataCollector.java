package com.groupnamenotfoundexception.wakeupcall.app.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaRecorder;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import com.groupnamenotfoundexception.wakeupcall.app.R;

import java.io.IOException;
import java.util.*;

/**
 * Created by Caner on 22/07/2015.
 */
public class AsleepModeDataCollector extends Service implements SensorEventListener{
    final static int CACHE_SIZE = 10;
    final int DELAY = 1000;

    private MyBinder binder = new MyBinder();

    final Handler handler = new Handler();
    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Log.i("COLLECTROLL","COLLECTS");
            collect();
            count++;

            oldestMeasurementIndex = count % CACHE_SIZE;
            if(count >= CACHE_SIZE){
                latestKey = new Date();

                collectionSound.put(latestKey,smooth(cacheSound));
                collectionMovement.put(latestKey,smooth(cacheMovement));
            }

            if(isWorking)
                handler.postDelayed(runnable,DELAY);
        }
    };

    double[] cacheSound;
    double[] cacheMovement;
    int oldestMeasurementIndex, count;
    Map<Date, Double> collectionSound;
    Map<Date, Double> collectionMovement;

    public Date latestKey;

    boolean isWorking;

    SensorManager sensorManager;
    Sensor accelerometer;

    MediaRecorder mRecorder;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("ASLEEP MODE COLLECTOR", "CALISIYOR");
        super.onCreate();
        cacheSound = new double[CACHE_SIZE];
        cacheMovement = new double[CACHE_SIZE];
        oldestMeasurementIndex = 0;
        count = 0;
        collectionSound = new HashMap<Date, Double>();
        collectionMovement = new HashMap<Date, Double>();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mRecorder.setOutputFile("/dev/null");
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        start();
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void start(){
        isWorking = true;
        mRecorder.start();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        handler.postDelayed(runnable,DELAY);
    }

    public void stop(){
        isWorking = false;
        handler.removeCallbacks(runnable);
        mRecorder.stop();
        sensorManager.unregisterListener(this);
        stopSelf();
    }

    private void collect(){
        if (mRecorder != null)
            cacheSound[oldestMeasurementIndex] = mRecorder.getMaxAmplitude();
    }

    public double smooth( double[] cache){
        double riemannSum = 0;

        for (int i = 0; i < CACHE_SIZE; i++) {
            riemannSum += cache[i];
        }

        return riemannSum;
    }

    public double average( Map <Date,Double> collection){
        Iterator<Date> i = collection.keySet().iterator();
        double sum = 0, size = collection.size();

        while (i.hasNext()){
            sum += collection.get(i.next());
        }

        return sum / size;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        cacheMovement[oldestMeasurementIndex] = event.values[2];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public Map<Date, Double> getCollectionSound() {
        return collectionSound;
    }

    public Map<Date, Double> getCollectionMovement() {
        return collectionMovement;
    }

    public class MyBinder extends Binder {
        public AsleepModeDataCollector getService() {
            return AsleepModeDataCollector.this;
        }
    }

    @Override
    public void onDestroy() {
        Set<Date> keys = collectionSound.keySet();
        StringBuilder stringBuilderSound = new StringBuilder(), stringBuilderMovement = new StringBuilder();
        for(Date d : keys){
            stringBuilderSound.append(collectionSound.get(d));
            stringBuilderSound.append(",");
            stringBuilderMovement.append(collectionMovement.get(d));
            stringBuilderMovement.append(",");
        }
        getSharedPreferences(getString(R.string.alarms_master), Context.MODE_PRIVATE).
                edit().putString( getString(R.string.collection_sound), stringBuilderSound.toString()).
                putString( getString(R.string.collection_movement), stringBuilderMovement.toString()).commit();
        super.onDestroy();
    }
}
