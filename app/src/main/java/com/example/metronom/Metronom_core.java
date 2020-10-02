package com.example.metronom;

import android.media.SoundPool;

import java.util.Timer;
import java.util.TimerTask;

public class Metronom_core {
    public void play(final SoundPool soundPool, final int IdPlay, int frequency){
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                soundPool.play(IdPlay, 1, 1, 1, 0, 1);
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 60000/frequency);
    }
    public void stop(){

    }

}
