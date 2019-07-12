package com.groupnamenotfoundexception.wakeupcall.app.activities.games;

import android.os.Bundle;
import android.view.View;
import android.view.animation.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.groupnamenotfoundexception.wakeupcall.app.R;
import com.groupnamenotfoundexception.wakeupcall.app.games.LazyKiller;


public class LazyKillerActivity extends GameActivity {
    //properties for the activity
    Button b1;
    View game, question;
    EditText t1;
    String text;

    ProgressBar progressBar;
    int delay, progress;
    Animation fadeOut, fadeIn;
    AnimationSet animation;

    LazyKiller lazykiller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lazy_killer);

        //setting the buttons, text, progressBar
        b1 = (Button) findViewById(R.id.button2);
        t1 = (EditText) findViewById(R.id.editText);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //Text that obtaining from the EditTexk
        text = ""+t1.getText().toString();

        //view
        game = findViewById(R.id.gameview);
        question = findViewById(R.id.questionView);
        //lazykiller
        lazykiller = new LazyKiller(difficulty, this, requester);
        //delay and progress as int
        delay = lazykiller.getDelay();
        progress = 0;
        //view for the color that asking
        question.setBackgroundColor(lazykiller.displayQuestion());
        //animation
        animation();
    }

    public void playLK(View view) {
        try {
            lazykiller.play(Integer.parseInt(t1.getText().toString()));
        } catch (Exception e) {
            lazykiller.play(0);
        }
    }

    public void animation() {

        game.setVisibility(View.VISIBLE);
        fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(delay / 2);

        fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setStartOffset(delay / 2);
        fadeOut.setDuration(delay / 2);

        animation = new AnimationSet(false);
        animation.addAnimation(fadeIn);
        animation.addAnimation(fadeOut);
        game.setAnimation(animation);
        game.setVisibility(View.INVISIBLE);
        //get delay method �nsert
        progress += 20;
        progressBar.setProgress(progress);
    }

    public void update(int color) {
        animation();
        game.setBackgroundColor(color);
        // game.setAnimation(animation);
    }

    @Override
    protected void stopGame() {
        lazykiller.removeTimeOut();
    }
}