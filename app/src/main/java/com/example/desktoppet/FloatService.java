package com.example.desktoppet;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

public class FloatService extends Service {

    private Handler floatWindow = new Handler();

    private Timer clockForCreatFloatwindow;

    /*@Override
    public IBinder onBind(Intent intent){return null;}*/

    @Override
    public int startComd(Intent intent, int flags, int startId) {
        if (clockForCreatFloatwindow == null) {
            clockForCreatFloatwindow = new Timer();
            clockForCreatFloatwindow.scheduleAtFixedRate(new Refresh(), 0, 500);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clockForCreatFloatwindow.cancel();
        clockForCreatFloatwindow = null;
    }

    class Refresh extends TimerTask {
        @Override
    }
}
