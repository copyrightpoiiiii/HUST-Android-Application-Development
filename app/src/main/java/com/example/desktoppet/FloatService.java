package com.example.desktoppet;

import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

public class FloatService extends Service {

    private Handler floatWindow = new Handler();

    private Timer clockForCreateFloatWindow;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (clockForCreateFloatWindow == null) {
            clockForCreateFloatWindow = new Timer();
            clockForCreateFloatWindow.scheduleAtFixedRate(new Refresh(), 0, 500);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clockForCreateFloatWindow.cancel();
        clockForCreateFloatWindow = null;
    }


    @SuppressWarnings("deprecation")
    private boolean isHome() {
        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);
        return getHomes().contains(rti.get(0).topActivity.getPackageName());
    }

    private List<String> getHomes() {
        List<String> names = new ArrayList<String>();
        PackageManager packageManager = this.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo info : resolveInfo)
            names.add(info.activityInfo.packageName);
        return names;
    }

    class Refresh extends TimerTask {
        @Override
        public void run() {
            if (isHome() && !myWindowManager.isWindowShowing()) {
                floatWindow.post(new Runnable() {
                    @Override
                    public void run() {
                        myWindowManager.createSmallWindow(getApplicationContext());
                    }
                });
            } else if (!isHome() && myWindowManager.isWindowShowing()) {
                floatWindow.post(new Runnable() {
                    @Override
                    public void run() {
                        myWindowManager.removeSmallWindow(getApplicationContext());
                        myWindowManager.removeBigWindow(getApplicationContext());
                    }
                });
            }
        }
    }
}
