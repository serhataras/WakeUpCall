package com.groupnamenotfoundexception.wakeupcall.app.games;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.widget.Toast;
import com.groupnamenotfoundexception.wakeupcall.app.R;
import com.groupnamenotfoundexception.wakeupcall.app.activities.AlarmAlertActivity;
import com.groupnamenotfoundexception.wakeupcall.app.activities.HomeActivity;
import com.groupnamenotfoundexception.wakeupcall.app.activities.games.*;

public abstract class Game {
    protected final Handler handler = new Handler();
    protected final Runnable timeOutCall = new Runnable() {
        @Override
        public void run() {
            timeOut();
        }
    };

    private final static long TIME_OUT_THRESHOLD = 10000;
    public final static int DIFFICULTY_EASY = 1;
    public final static int DIFFICULTY_MEDIUM = 2;
    public final static int DIFFICULTY_HARD = 3;

    public static final int REQUESTER_GAME_LIST = 0;
    public static final int REQUESTER_RANDOM_ARCADE = 1;
    public static final int REQUESTER_ADVANCED_ALARM = 2;

    protected int difficulty;
    protected GameActivity gameActivity;
    protected int requester;

    private SharedPreferences pref;
    private SharedPreferences.Editor edit;

    protected void gameWon() {
        handler.removeCallbacks(timeOutCall);
        Toast.makeText(gameActivity, "WON", Toast.LENGTH_SHORT).show();

        if (requester == REQUESTER_GAME_LIST) {
            gameActivity.finish();
        }else if( requester == REQUESTER_RANDOM_ARCADE){
            edit.putInt(gameActivity.getString(R.string.current_score),pref.getInt(gameActivity.getString(R.string.current_score),0) + 1).commit();

            Intent intent;

            int randomNum = ((int) (Math.random() * 5));


            if (randomNum == 0) {
                intent = new Intent(gameActivity, SimonActivity.class);
            } else if (randomNum == 1) {
                intent = new Intent(gameActivity, MatchTheObjectsActivity.class);
            } else if (randomNum == 2) {
                intent = new Intent(gameActivity, ShakeTheDeviceActivity.class);
            } else if (randomNum == 3){
                intent = new Intent(gameActivity, PressTheBlueActivity.class);
            } else {
                intent = new Intent(gameActivity, LazyKillerActivity.class);
            }

            intent.putExtra("DIFFICULTY", Game.DIFFICULTY_MEDIUM);
            intent.putExtra("REQUESTER", Game.REQUESTER_RANDOM_ARCADE);

            gameActivity.startActivity(intent);
        }else if( requester == REQUESTER_ADVANCED_ALARM){
            Intent result = new Intent();
            result.putExtra("GAME RESULT",true);
            gameActivity.setResult(AlarmAlertActivity.GAMES_REQUEST_CODE,result);
            gameActivity.finish();
        }
    }

    public void gameLost() {
        handler.removeCallbacks(timeOutCall);
        Toast.makeText(gameActivity, "LOST", Toast.LENGTH_SHORT).show();

        if (requester == REQUESTER_GAME_LIST) {
            gameActivity.finish();
        }else if( requester == REQUESTER_RANDOM_ARCADE){
            int currentScore = pref.getInt(gameActivity.getString(R.string.current_score),0);

            if(currentScore > pref.getInt(gameActivity.getString(R.string.highscore),0))
                edit.putInt(gameActivity.getString(R.string.highscore),currentScore);

            edit.putInt(gameActivity.getString(R.string.current_score),0).commit();

            Intent intent = new Intent(gameActivity,HomeActivity.class);
            intent.putExtra("LastFragment",2);
            gameActivity.startActivity(intent);
        }else if( requester == REQUESTER_ADVANCED_ALARM){
            Intent result = new Intent();
            result.putExtra("GAME RESULT",false);
            gameActivity.setResult(AlarmAlertActivity.GAMES_REQUEST_CODE, result);
            gameActivity.finish();
        }
    }

    public void removeTimeOut() {
        handler.removeCallbacks(timeOutCall);
    }

    protected void timeOut() {
        // TODO : Game Controller'a haber ver!
        gameLost();
    }

    protected void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public abstract void play(int playIndex);

    public Game(int difficulty, GameActivity gameActivity, int requester) {
        setDifficulty(difficulty);
        this.gameActivity = gameActivity;
        this.requester = requester;
        pref = gameActivity.getSharedPreferences(gameActivity.getString(R.string.alarms_master), Context.MODE_PRIVATE);
        edit = pref.edit();

        handler.postDelayed(timeOutCall, TIME_OUT_THRESHOLD);
    }
}
