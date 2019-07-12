package com.groupnamenotfoundexception.wakeupcall.app.activities.games;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Caner on 25/07/2015.
 */
public abstract class GameActivity extends Activity{
    int difficulty,requester;

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        difficulty = getIntent().getExtras().getInt("DIFFICULTY",0);
        requester = getIntent().getExtras().getInt("REQUESTER",0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopGame();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopGame();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected abstract void stopGame();
}
