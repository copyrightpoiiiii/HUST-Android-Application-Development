package com.example.desktoppet;

import java.util.Timer;
import java.util.TimerTask;

import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;

public class Animation {

    Timer timer = new Timer();
    public int mills;
    public int source;
    ImageView holder;
    Animation(int source, int mills){
        this.mills = mills;
        this.source = source;
    }

    public void play(){
        if(FloatWindowPetView.flag){
            if(!((AnimationDrawable)FloatWindowPetView.petView.getBackground()).isRunning()){
                FloatWindowPetView.petView.setBackgroundResource(source);
                ((AnimationDrawable)FloatWindowPetView.petView.getBackground()).start();
                stopTimer();
            }
        }
    }

    public void stopTimer(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(FloatWindowPetView.flag)
                    ((AnimationDrawable)FloatWindowPetView.petView.getBackground()).stop();
            }
        },mills);
    }
}
