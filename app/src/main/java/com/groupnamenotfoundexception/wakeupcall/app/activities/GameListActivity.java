package com.groupnamenotfoundexception.wakeupcall.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.groupnamenotfoundexception.wakeupcall.app.R;
import com.groupnamenotfoundexception.wakeupcall.app.activities.games.*;
import com.groupnamenotfoundexception.wakeupcall.app.games.Game;

public class GameListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);
    }

    /**
     * Called by Floating Action Button, adds new Alarm
     *
     * @param view ,View that called the Method - Required for in XML Method Binding
     */
    public void startSimon(View view) {
        // Create the intent
        Intent intent = new Intent( this, SimonActivity.class);
        intent.putExtra("DIFFICULTY", Game.DIFFICULTY_MEDIUM);
        intent.putExtra("REQUESTER",Game.REQUESTER_GAME_LIST);

        // Start the Activity
        startActivity(intent);
    }

    /**
     * Called by Floating Action Button, adds new Alarm
     *
     * @param view ,View that called the Method - Required for in XML Method Binding
     */
    public void startShake(View view) {
        // Create the intent
        Intent intent = new Intent( this, ShakeTheDeviceActivity.class);
        intent.putExtra("DIFFICULTY", Game.DIFFICULTY_MEDIUM);
        intent.putExtra("REQUESTER",Game.REQUESTER_GAME_LIST);

        // Start the Activity
        startActivity(intent);
    }

    /**
     * Called by Floating Action Button, adds new Alarm
     *
     * @param view ,View that called the Method - Required for in XML Method Binding
     */
    public void startMatch(View view) {
        // Create the intent
        Intent intent = new Intent( this, MatchTheObjectsActivity.class);
        intent.putExtra("DIFFICULTY", Game.DIFFICULTY_MEDIUM);
        intent.putExtra("REQUESTER",Game.REQUESTER_GAME_LIST);

        // Start the Activity
        startActivity(intent);
    }

    /**
     * Called by Floating Action Button, adds new Alarm
     *
     * @param view ,View that called the Method - Required for in XML Method Binding
     */
    public void startBlue(View view) {
        // Create the intent
        Intent intent = new Intent( this, PressTheBlueActivity.class);
        intent.putExtra("DIFFICULTY", Game.DIFFICULTY_HARD);
        intent.putExtra("REQUESTER",Game.REQUESTER_GAME_LIST);

        // Start the Activity
        startActivity(intent);
    }

    /**
     * Called by Floating Action Button, adds new Alarm
     *
     * @param view ,View that called the Method - Required for in XML Method Binding
     */
    public void startLazy(View view) {
        // Create the intent
        Intent intent = new Intent( this, LazyKillerActivity.class);
        intent.putExtra("DIFFICULTY", Game.DIFFICULTY_MEDIUM);
        intent.putExtra("REQUESTER",Game.REQUESTER_GAME_LIST);

        // Start the Activity
        startActivity(intent);
    }
}
