package com.example.desktoppet;

import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.util.Log;

public class NormalTask extends AsyncTask<Void, Void, Boolean> {
    int cnt = 0;
    int animationIndex;

    @Override
    protected Boolean doInBackground(Void... params) {
        while (true) {
			/*
			try {
				int sleepTime = (int) (Math.random() * 5000 + 2500);
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
            if (FloatWindowPetView.flag) {
                //if(!((AnimationDrawable) FloatWindowSmallView.petView.getBackground()).isRunning()){
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                publishProgress();
            }
            //}
        }
    }

    protected void onProgressUpdate(Void... params) {
        if (FloatWindowPetView.flag) {
            cnt++;
            int petIndex = FloatWindowPetView.getPetIndex();
            int animationSize = SingleAnimaion.animationSet[petIndex].length;
            if (cnt >= 3 && !((AnimationDrawable) FloatWindowPetView.petView.getBackground()).isRunning()) {
                cnt = 0;
                //((AnimationDrawable)FloatWindowSmallView.petView.getBackground()).stop();
                animationIndex = (int) (Math.random() * animationSize);
                SingleAnimaion.animationSet[petIndex][animationIndex].play();
            } else
                SingleAnimaion.animationSet[petIndex][0].play();
            Log.d("animation index:" + animationIndex, String.valueOf(count));
        }
    }
}
