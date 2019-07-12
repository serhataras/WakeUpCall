package com.groupnamenotfoundexception.wakeupcall.app.games;

import android.graphics.Color;
import com.groupnamenotfoundexception.wakeupcall.app.activities.games.GameActivity;

import java.util.ArrayList;

/**
 * Created by User on 25.7.2015.
 */
public class PressTheBlue extends Game{
    private final static int[] COLORS = new int[]{Color.BLUE,Color.GREEN,Color.RED,Color.YELLOW,Color.MAGENTA};
    private ArrayList<Integer> buttons;
    private int blueCount;

    public PressTheBlue(int difficulty, GameActivity gameActivity,int requester) {
        super(difficulty, gameActivity,requester);
    }

    @Override
    public void play(int playIndex) {
        if(buttons.get(playIndex) == COLORS[0]){
            blueCount--;
            if(blueCount <= 0)
                gameWon();
        }else{
            gameLost();
        }
    }

    @Override
    protected void setDifficulty(int difficulty) {
        super.setDifficulty(difficulty);

        buttons = new ArrayList<Integer>();
        int a;

        for (int i = 0; i < Math.pow(2 + difficulty, 2); i++) {
            a = (int) (Math.random() * (2 + difficulty));
            buttons.add(COLORS[a]);
            if(a == 0)
                blueCount++;
        }

        a = (int) (Math.random() * (2 + difficulty) * (2 + difficulty));
        if(!(buttons.get(a).equals(COLORS[0]))){
            buttons.set(a,COLORS[0]);
            blueCount++;
        }
    }

    public ArrayList<Integer> getButtons() {
        return buttons;
    }
}
