package com.groupnamenotfoundexception.wakeupcall.app.activities.games;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import com.groupnamenotfoundexception.wakeupcall.app.R;
import com.groupnamenotfoundexception.wakeupcall.app.games.SimonGame;


public class SimonActivity extends GameActivity {

    Handler handler;
    final Runnable runnable =  new Runnable() {
        @Override
        public void run() {
            if(simon[count] == 0){
                button1.setPressed(true);
                handler.postDelayed(unpress, 375);
            }else if(simon[count] == 1){
                button2.setPressed(true);
                handler.postDelayed(unpress, 375);
            }
            else if(simon[count] == 2){
                button3.setPressed(true);
                handler.postDelayed(unpress, 375);
            }
            else if(simon[count] == 3){
                button4.setPressed(true);
                handler.postDelayed(unpress, 375);
            }
            if(++count < simon.length)
                handler.postDelayed(runnable,750);
        }
    };

    final Runnable unpress = new Runnable() {
        @Override
        public void run() {
            if(simon[count - 1] == 0){
                button1.setPressed(false);
            }else if(simon[count - 1] == 1){
                button2.setPressed(false);
            }
            else if(simon[count - 1] == 2){
                button3.setPressed(false);
            }
            else if(simon[count - 1] == 3){
                button4.setPressed(false);
            }
        }
    };

    ImageButton button1,button2,button3,button4;
    protected SimonGame simonGame;
    int[] simon;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new Handler();
        setContentView(R.layout.activity_simon);
        simonGame= new SimonGame(difficulty,this,requester);
        simon = simonGame.getRandomOrder();

        button1 = (ImageButton) findViewById(R.id.simonbutton1);
        button2 = (ImageButton) findViewById(R.id.simonbutton2);
        button3 = (ImageButton) findViewById(R.id.simonbutton3);
        button4 = (ImageButton) findViewById(R.id.simonbutton4);

    }

    public void onStart(){
        super.onStart();
        handler.postDelayed(runnable, 750);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.simonbutton1)
                    simonGame.play(0);
                else if(view.getId() == R.id.simonbutton2)
                    simonGame.play(1);
                else if(view.getId() == R.id.simonbutton3)
                    simonGame.play(2);
                else if(view.getId() == R.id.simonbutton4)
                    simonGame.play(3);
            }
        };

        button1.setOnClickListener(clickListener);
        button2.setOnClickListener(clickListener);
        button3.setOnClickListener(clickListener);
        button4.setOnClickListener(clickListener);
    }

    @Override
    protected void stopGame() {
        simonGame.removeTimeOut();
    }
}