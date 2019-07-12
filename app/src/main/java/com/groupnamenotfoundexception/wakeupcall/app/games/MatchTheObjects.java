package com.groupnamenotfoundexception.wakeupcall.app.games;

import android.os.Handler;
import com.groupnamenotfoundexception.wakeupcall.app.activities.games.GameActivity;

import java.util.Random;

public class MatchTheObjects extends Game {
    final Handler handler = new Handler();
    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            timeOut();
        }
    };

    int[] objects;
    boolean isFirstMove;
    int firstIndex,foundPair;

    public MatchTheObjects(int difficulty, GameActivity activity, int requester) {
        super(difficulty, activity,requester);
        isFirstMove = true;
        foundPair = 0;
    }

    @Override
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;

        objects = new int[4 + 2 * difficulty];
        int length = objects.length;
        for (int i = 0; i < objects.length; i++) {
            objects[i] = i / 2;
        }

        Random random = new Random();
        int temp, place1, place2;

        for (int i = 0; i < 30; i++) {
            place1 = random.nextInt(length);
            place2 = random.nextInt(length);
            temp = objects[place1];
            objects[place1] = objects[place2];
            objects[place2] = temp;
        }
    }

    public void play(int index) {
        if (isFirstMove) {
            firstIndex = index;
        } else {
            if (objects[firstIndex] == objects[index]) {
                objects[firstIndex] = -1;
                objects[index] = -1;

                if (++foundPair >= 2 + difficulty) {
                    gameWon();
                }
            } else
                gameLost();
        }
        isFirstMove = !isFirstMove;
    }

    public int[] getObjects() {
        return objects;
    }
}