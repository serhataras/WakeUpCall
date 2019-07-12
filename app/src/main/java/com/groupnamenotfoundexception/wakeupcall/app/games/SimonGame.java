package com.groupnamenotfoundexception.wakeupcall.app.games;

import com.groupnamenotfoundexception.wakeupcall.app.activities.games.GameActivity;

public class SimonGame extends Game {

    private int [] randomOrder;
    private int count;

    public SimonGame(int difficulty,GameActivity gameActivity,int requester){
        super(difficulty,gameActivity,requester);

        randomOrder = new int[difficulty*2];
        count = 0;

        for(int i = 0; i<randomOrder.length;i++){
            randomOrder[i] = (int)(Math.random()*4);
        }
    }

    public int [] getRandomOrder(){
        return randomOrder;
    }

    @Override
    public void play(int i){
        if(randomOrder[count] != i)
            gameLost();
        else if(count >= difficulty * 2 - 1)
            gameWon();
        count++;
    }
}