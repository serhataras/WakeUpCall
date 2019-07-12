package com.groupnamenotfoundexception.wakeupcall.app.games;

import android.graphics.Color;
import android.os.Handler;
import com.groupnamenotfoundexception.wakeupcall.app.activities.games.GameActivity;
import com.groupnamenotfoundexception.wakeupcall.app.activities.games.LazyKillerActivity;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Serhat on 24.7.2015.
 * com.groupnamenotfoundexception.wakeupcall.app.activities
 * wakeupcall
 */
public class LazyKiller extends Game {
    private int answer,pickedColor,currentColor;
    private  ArrayList<Integer> sets,random;
    private int count=0,showCount=0, delay =0;

    /**
     * a constructor for the lazykiller
     * @param difficulty
     * @param gameActivity
     * @param requester
     */
    public LazyKiller (int difficulty,GameActivity gameActivity, int requester){
        super(difficulty,gameActivity,requester);
        sets = new ArrayList<Integer>();
        random= new ArrayList<Integer>();
        currentColor=0;
        setDelay();
        setColors();
        randomize();
        start();
        update();
    }

    /**
     * adding the colors into the sets array
     * @return
     */
    public ArrayList<Integer> setColors() {
        sets= new ArrayList<Integer>();

        int c1= Color.CYAN;
        int c2= Color.BLUE;
        int c3= Color.GREEN;
        int c4=Color.RED ;
        int c5=Color.MAGENTA;

        sets.add(c1);
        sets.add(c2);
        sets.add(c3);
        sets.add(c4);
        sets.add(c5);


        return sets;
    }

    /**
     * randomize and adding the array's elements into new array
     * @return
     */
    public ArrayList<Integer> randomize(){
        Random rdm= new Random();

        int bc= rdm.nextInt(5);
        pickedColor=sets.get(bc);
        for (int i = 0; i <5; i++) {
            random.add(sets.get(rdm.nextInt(5)));
            if(random.get(i)==pickedColor)
                count++;
        }
        return random;
    }

    /**
     * return the pickedcolor as int
     * @return
     */
    public int getPickedColor(){
        return pickedColor;
    }

    /**
     * this method is going to return false if the answer is false otherwise is means the
     * user is going to win and return true
     * @return
     */
    public boolean hasLost(){
        if(answer==count)
            return false;
        return true;
    }

    /**
     * return the delay as integer
     * @return
     */
    public int getDelay(){
        return delay;
    }

    /**
     * setting the delay according to the user's difficult selection
     */
    public void setDelay(){
        if(difficulty==DIFFICULTY_HARD)
            delay =800;
        else if(difficulty==DIFFICULTY_MEDIUM)
            delay =1000;
        else
            delay =1200;
    }

    /**
     * Handler part (Like a Timer Class of the java)
     */
    final Handler handler = new Handler();
    // Handles cpu!

    final Runnable runnable = new Runnable() {
        // Calls the run method when called
        public void run() {
            //do what the handler is going to do
            update();
            if (showCount < 5)
                handler.postDelayed(runnable, delay);// DElay integer
        }
    };

    //To start the handler
    public void start() {
        handler.postDelayed(runnable, delay);
    }
    //To update the view
    public void update() {
        currentColor = random.get(showCount++);
        ((LazyKillerActivity) gameActivity).update(currentColor);
    }
    //play button
    public void play(int i) {
        answer = i;
        if (hasLost()) {
            gameLost();
        } else {
            gameWon();
        }
    }

    /**
     * to display color that is asking for
     * @return
     */
    public  int displayQuestion(){
        return getPickedColor();
    }

}