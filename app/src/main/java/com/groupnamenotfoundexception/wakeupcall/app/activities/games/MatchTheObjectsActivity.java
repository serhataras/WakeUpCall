package com.groupnamenotfoundexception.wakeupcall.app.activities.games;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.groupnamenotfoundexception.wakeupcall.app.R;
import com.groupnamenotfoundexception.wakeupcall.app.games.Game;
import com.groupnamenotfoundexception.wakeupcall.app.games.MatchTheObjects;

public class MatchTheObjectsActivity extends GameActivity {

    private ImageButton b0,b1,b2,b3,b4,b5,b6,b7,b8,b9;
    private ImageButton[] buttonsArray;
    private MatchTheObjects matchTheObjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_the_objects);

        matchTheObjects = new MatchTheObjects(difficulty, this, requester);

        b0 = (ImageButton)findViewById(R.id.matchbutton0);
        b1 = (ImageButton)findViewById(R.id.matchbutton1);
        b2 = (ImageButton)findViewById(R.id.matchbutton2);
        b3 = (ImageButton)findViewById(R.id.matchbutton3);
        b4 = (ImageButton)findViewById(R.id.matchbutton4);
        b5 = (ImageButton)findViewById(R.id.matchbutton5);
        b6 = (ImageButton)findViewById(R.id.matchbutton6);
        b7 = (ImageButton)findViewById(R.id.matchbutton7);
        b8 = (ImageButton)findViewById(R.id.matchbutton8);
        b9 = (ImageButton)findViewById(R.id.matchbutton9);

        if(matchTheObjects.getDifficulty() == Game.DIFFICULTY_EASY){
            buttonsArray = new ImageButton[]{b0,b1,b8,b9,b4,b5};
            b2.setVisibility(View.INVISIBLE);
            b2.setEnabled(false);
            b3.setVisibility(View.INVISIBLE);
            b3.setEnabled(false);
            b6.setVisibility(View.INVISIBLE);
            b6.setEnabled(false);
            b7.setVisibility(View.INVISIBLE);
            b7.setEnabled(false);
        } else if(matchTheObjects.getDifficulty() == Game.DIFFICULTY_MEDIUM){
            buttonsArray = new ImageButton[]{b0,b1,b2,b3,b4,b5,b6,b7};
            b8.setVisibility(View.INVISIBLE);
            b8.setEnabled(false);
            b9.setVisibility(View.INVISIBLE);
            b9.setEnabled(false);
        }
        else if(matchTheObjects.getDifficulty() == Game.DIFFICULTY_HARD){
            buttonsArray = new ImageButton[]{b0,b1,b2,b3,b4,b5,b6,b7,b8,b9};
        }
        addListenerOnButton();
    }

    public void addListenerOnButton() {
        int[] array = matchTheObjects.getObjects();

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                v.setVisibility(View.INVISIBLE);

                for (int i = 0; i < buttonsArray.length; i++) {
                    if(v.getId() == buttonsArray[i].getId()){
                        matchTheObjects.play(i);
                        break;
                    }
                }
            }
        };
        for (int i = 0; i < matchTheObjects.getDifficulty() * 2 + 4; i++) {
            if (array[i] == 0) {
                buttonsArray[i].setImageResource(R.drawable.square);
            } else if (array[i] == 1) {
                buttonsArray[i].setImageResource(R.drawable.oval_ripple);
            } else if (array[i] == 2) {
                buttonsArray[i].setImageResource(R.drawable.triangle);
            } else if (array[i] == 3) {
                buttonsArray[i].setImageResource(R.drawable.octagon);
            } else if (array[i] == 4) {
                buttonsArray[i].setImageResource(R.drawable.rounded);
            }

            buttonsArray[i].setOnClickListener(clickListener);
        }
    }

    @Override
    protected void stopGame() {
        matchTheObjects.removeTimeOut();
    }
}
