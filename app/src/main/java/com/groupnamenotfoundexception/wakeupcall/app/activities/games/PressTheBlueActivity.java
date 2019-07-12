package com.groupnamenotfoundexception.wakeupcall.app.activities.games;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.groupnamenotfoundexception.wakeupcall.app.R;
import com.groupnamenotfoundexception.wakeupcall.app.games.Game;
import com.groupnamenotfoundexception.wakeupcall.app.games.PressTheBlue;

import java.util.ArrayList;

/**
 * Created by User on 24.7.2015.
 */
public class PressTheBlueActivity extends GameActivity implements View.OnClickListener {

    ArrayList<Button> buttons = new ArrayList<Button>();
    PressTheBlue pressTheBlue;
    Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13,b14,b15,b16,b17,b18,b19,b20,b21,b22,b23,b24,b25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pressTheBlue = new PressTheBlue(difficulty,this,requester);
        if(pressTheBlue.getDifficulty() == Game.DIFFICULTY_EASY)
            setContentView(R.layout.activity_press_the_blue3);
        else if(pressTheBlue.getDifficulty() == Game.DIFFICULTY_MEDIUM)
            setContentView(R.layout.activity_press_the_blue4);
        else
            setContentView(R.layout.activity_press_the_blue5);

        b1 = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);
        b5 = (Button) findViewById(R.id.button5);
        b6 = (Button) findViewById(R.id.button6);
        b7 = (Button) findViewById(R.id.button7);
        b8 = (Button) findViewById(R.id.button8);
        b9 = (Button) findViewById(R.id.button9);


        buttons.add(b1);
        buttons.add(b2);
        buttons.add(b3);
        buttons.add(b4);
        buttons.add(b5);
        buttons.add(b6);
        buttons.add(b7);
        buttons.add(b8);
        buttons.add(b9);

        if(pressTheBlue.getDifficulty() >= Game.DIFFICULTY_MEDIUM) {
            b10 = (Button) findViewById(R.id.button10);
            b11 = (Button) findViewById(R.id.button11);
            b12 = (Button) findViewById(R.id.button12);
            b13 = (Button) findViewById(R.id.button13);
            b14 = (Button) findViewById(R.id.button14);
            b15 = (Button) findViewById(R.id.button15);
            b16 = (Button) findViewById(R.id.button16);


            buttons.add(b10);
            buttons.add(b11);
            buttons.add(b12);
            buttons.add(b13);
            buttons.add(b14);
            buttons.add(b15);
            buttons.add(b16);
        }
        if(pressTheBlue.getDifficulty() == Game.DIFFICULTY_HARD) {
            b17 = (Button) findViewById(R.id.button17);
            b18 = (Button) findViewById(R.id.button18);
            b19 = (Button) findViewById(R.id.button19);
            b20 = (Button) findViewById(R.id.button20);
            b21 = (Button) findViewById(R.id.button21);
            b22 = (Button) findViewById(R.id.button22);
            b23 = (Button) findViewById(R.id.button23);
            b24 = (Button) findViewById(R.id.button24);
            b25 = (Button) findViewById(R.id.button25);


            buttons.add(b17);
            buttons.add(b18);
            buttons.add(b19);
            buttons.add(b20);
            buttons.add(b21);
            buttons.add(b22);
            buttons.add(b23);
            buttons.add(b24);
            buttons.add(b25);
        }

        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setBackgroundColor(pressTheBlue.getButtons().get(i));
            buttons.get(i).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        v.setEnabled(false);
        v.setBackgroundColor(Color.GRAY);
        for (int i = 0; i < buttons.size(); i++) {
            if(v.getId() == buttons.get(i).getId()){
                pressTheBlue.play(i);
                break;
            }
        }
    }

    @Override
    protected void stopGame() {
        pressTheBlue.removeTimeOut();
    }
}

