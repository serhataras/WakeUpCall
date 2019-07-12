package com.groupnamenotfoundexception.wakeupcall.app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import com.groupnamenotfoundexception.wakeupcall.app.R;
import com.groupnamenotfoundexception.wakeupcall.app.adapters.CreditsAdapter;

public class CreditsActivity extends Activity {

    RecyclerView credits;
    CreditsAdapter creditsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        credits = (RecyclerView) findViewById(R.id.credits);
        credits.setLayoutManager(new LinearLayoutManager(this));
        credits.setHasFixedSize(true);

        creditsAdapter = new CreditsAdapter();

        credits.setAdapter(creditsAdapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            super.onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }
}