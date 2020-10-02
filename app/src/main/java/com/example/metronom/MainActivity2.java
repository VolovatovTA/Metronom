package com.example.metronom;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ToggleButton;


import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity2 extends AppCompatActivity implements SoundPool.OnLoadCompleteListener, CompoundButton.OnCheckedChangeListener {

    String TAG = "myTAG";
    ToggleButton mToggleButton;
    int soundIdShot;
    private Timer timer = new Timer();
    long freq = 100;

    final SoundPool sp = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        sp.setOnLoadCompleteListener(this);
        soundIdShot = sp.load(this, R.raw.m4, 1);

        mToggleButton = findViewById(R.id.toggleButton);
        mToggleButton.setOnCheckedChangeListener(this);
    }



    @Override
    public void onLoadComplete(SoundPool soundPool, int i, int i1) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (timer != null) timer.cancel();

        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                sp.play(soundIdShot, 1, 1, 1, 0, 1);
            }
        };

        if (b) {
            timer.scheduleAtFixedRate(timerTask, 0, 60000/freq);
        }
        else{
            timer.cancel();
            timer = null;
        }

    }
}