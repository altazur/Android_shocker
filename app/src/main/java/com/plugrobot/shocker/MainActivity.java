package com.plugrobot.shocker;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Flash MyFLash ;
    Vibrator MyVibr;
    boolean enabled;
    TextView TextBox;
    ImageView Bolt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enabled = false;
        Button myBtn = (Button) findViewById(R.id.button);

        MyFLash = new Flash(this);
        MyVibr = (Vibrator)  getSystemService(Context.VIBRATOR_SERVICE);

        TextBox = (TextView) findViewById(R.id.textView);

        myBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction()==MotionEvent.ACTION_DOWN) {
                    Shocker(true);
                }
                else if (event.getAction()==MotionEvent.ACTION_UP){
                    Shocker(false);
                }
                return true;
            }
        });
    }

    public void Shocker(boolean on){
        Bolt = (ImageView) findViewById(R.id.imageView);
        if (on){
            enabled = true;
            TextBox.setText("Enabled");
            Bolt.setVisibility(View.VISIBLE);
            MyVibr.vibrate(100L);
            MyFLash.FlashOn();
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();}

            MyVibr.cancel();
            MyFLash.FlashOff();
        }
        else if (!on){
            enabled = false;
            TextBox.setText("Disabled");
            Bolt.setVisibility(View.INVISIBLE);
            MyFLash.FlashOff();
            MyVibr.cancel();
        }
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_UP) {
                    if (!enabled) {
                            if (!MyFLash.isEnabled) {
                                MyFLash.FlashOn();
                            } else MyFLash.FlashOff();
                    }
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    Shocker(true);
                }
                else if (action == KeyEvent.ACTION_UP){
                    Shocker(false);
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }
}
