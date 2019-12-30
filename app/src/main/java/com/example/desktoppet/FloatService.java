package com.example.desktoppet;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.IBinder;
import android.view.accessibility.AccessibilityEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

public class FloatService extends Service {

    private Handler floatWindow = new Handler();

    private Timer clockForCreateFloatWindow;

    private Timer alarmChong;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (clockForCreateFloatWindow == null) {
            clockForCreateFloatWindow = new Timer();
            clockForCreateFloatWindow.scheduleAtFixedRate(new Refresh(), 0, 500);
        }
        if (alarmChong == null) {
            alarmChong = new Timer();
            alarmChong.scheduleAtFixedRate(new SetAlarm(), 0, 1000 * 5 * 60);//* 60 * Integer.parseInt(Consts.CLOCK_KEY));

        }
        return super.onStartCommand(intent, flags, startId);
    }

    public class SetAlarm extends TimerTask {
        @Override
        public void run() {
            //
            /*
             * 获取消息内容
             */
            floatWindow.post(new Runnable() {
                @Override
                public void run() {
                    myWindowManager.createSmallWindow(getApplicationContext());
                    String text;
                    if (isNumeric(Consts.CLOCK_KEY) && Integer.parseInt(Consts.CLOCK_KEY) > 0)
                        text = "你已经玩了" + Consts.CLOCK_KEY + "分钟手机啦，休息一会吧!";
                    else text = "你已经玩了5分钟手机啦，休息一会吧!";
                    myWindowManager.createMessageWindow(getBaseContext(), text);
                    if (isNumeric(Consts.CLOCK_KEY) && Integer.parseInt(Consts.CLOCK_KEY) > 0)
                        alarmChong.scheduleAtFixedRate(new SetAlarm(), 0, 1000 * 60 * Integer.parseInt(Consts.CLOCK_KEY));
                }
            });
        }
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
            /*if (isHome() && !myWindowManager.isWindowShowing()) {
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

             */
            floatWindow.post(new Runnable() {
                @Override
                public void run() {
                    myWindowManager.createSmallWindow(getApplicationContext());
                }
            });
        }
    }


}
