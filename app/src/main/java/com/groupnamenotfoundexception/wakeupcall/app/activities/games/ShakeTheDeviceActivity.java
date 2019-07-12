package com.groupnamenotfoundexception.wakeupcall.app.activities.games;

import android.os.Bundle;
import android.widget.TextView;
import com.groupnamenotfoundexception.wakeupcall.app.R;
import com.groupnamenotfoundexception.wakeupcall.app.games.ShakeTheDevice;

public class ShakeTheDeviceActivity extends GameActivity {

    TextView count;
    ShakeTheDevice shakeTheDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake_the_device);

        shakeTheDevice = new ShakeTheDevice(difficulty,this,requester);

        count = (TextView) findViewById(R.id.shake_count);

        updateView();
    }
    public void updateView(){
        if(shakeTheDevice.getNeededShakeCount() > 0)
            count.setText(String.valueOf(shakeTheDevice.getNeededShakeCount()));
        else
            count.setText("0");
    }

    @Override
    protected void stopGame() {
        shakeTheDevice.removeTimeOut();
    }
}
