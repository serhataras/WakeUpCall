package com.groupnamenotfoundexception.wakeupcall.app.games;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import com.groupnamenotfoundexception.wakeupcall.app.activities.games.GameActivity;
import com.groupnamenotfoundexception.wakeupcall.app.activities.games.ShakeTheDeviceActivity;

/**
 * Created by Alchemistake on 24/07/15.
 */
public class ShakeTheDevice extends Game {
    private int neededShakeCount;
    private SensorManager sensorManager;
    private ShakeDetector shakeDetector;

    public ShakeTheDevice(int difficulty, GameActivity gameActivity, int requester) {
        super(difficulty, gameActivity,requester);
        neededShakeCount = difficulty * difficulty * 10;

        sensorManager = (SensorManager) gameActivity.getSystemService(Context.SENSOR_SERVICE);
        shakeDetector = new ShakeDetector();
        sensorManager.registerListener(shakeDetector,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void play(int playIndex) {
        neededShakeCount -= playIndex;

        ((ShakeTheDeviceActivity)gameActivity).updateView();

        if(neededShakeCount <= 0)
            gameWon();
    }

    @Override
    protected void gameWon() {
        sensorManager.unregisterListener(shakeDetector);
        super.gameWon();
    }

    @Override
    public void gameLost() {
        sensorManager.unregisterListener(shakeDetector);
        super.gameLost();
    }

    public int getNeededShakeCount() {
        return neededShakeCount;
    }

    public class ShakeDetector implements SensorEventListener {

        /*
         * The gForce that is necessary to register as shake.
         * Must be greater than 1G (one earth gravity unit).
         * You can install "G-Force", by Blake La Pierre
         * from the Google Play Store and run it to see how
         *  many G's it takes to register a shake
         */
        private static final float SHAKE_THRESHOLD_GRAVITY = 3F;
        private static final int SHAKE_SLOP_TIME_MS = 10;
        private static final int SHAKE_COUNT_RESET_TIME_MS = 3000;

        private long mShakeTimestamp;
        private int mShakeCount;


        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // ignore
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                float gX = x / SensorManager.GRAVITY_EARTH;
                float gY = y / SensorManager.GRAVITY_EARTH;
                float gZ = z / SensorManager.GRAVITY_EARTH;

                // gForce will be close to 1 when there is no movement.
                float gForce = (float)Math.sqrt(gX * gX + gY * gY + gZ * gZ);

                if (gForce > SHAKE_THRESHOLD_GRAVITY) {
                    final long now = System.currentTimeMillis();
                    // ignore shake events too close to each other (500ms)
                    if (mShakeTimestamp + SHAKE_SLOP_TIME_MS > now) {
                        return;
                    }

                    // reset the shake count after 3 seconds of no shakes
                    if (mShakeTimestamp + SHAKE_COUNT_RESET_TIME_MS < now) {
                        mShakeCount = 0;
                    }

                    mShakeTimestamp = now;
                    mShakeCount++;

                    play(mShakeCount);
                }
            }
        }
    }
