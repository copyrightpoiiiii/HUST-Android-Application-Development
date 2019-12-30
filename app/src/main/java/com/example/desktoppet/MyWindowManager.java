package com.example.desktoppet;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

public class myWindowManager {

    /**
     * 小悬浮窗View的实例
     */
    private static FloatWindowPetView smallWindow;

    /**
     * 大悬浮窗View的实例
     */
    private static FloatToolbarView bigWindow;

    /**
     * 小悬浮窗View的参数
     */
    private static LayoutParams smallWindowParams;

    /**
     * 消息框View的实例
     */
    private static FloatWindowMessageView messageWindow;

    private static bluetoothMessageWindow bluetoothMessageWindow;

    private static LayoutParams bluetoothMessageWindowParams;

    /**
     * 大悬浮窗View的参数
     */
    private static LayoutParams bigWindowParams;
    /**
     * 消息框View的参数
     */
    private static LayoutParams messageWindowParams;

    /**
     * 用于控制在屏幕上添加或移除悬浮窗
     */
    private static WindowManager mWindowManager;

    /**
     * 用于获取手机可用内存
     */
    private static ActivityManager mActivityManager;

    private static boolean flag = false;

    /**
     * 创建一个小悬浮窗。初始位置为屏幕的右部中间位置。
     *
     * @param context 必须为应用程序的Context.
     */
    public static void createSmallWindow(Context context) {

        WindowManager windowManager = getWindowManager(context);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        if (smallWindow == null) {
            smallWindow = new FloatWindowPetView(context);
            if (smallWindowParams == null) {
                smallWindowParams = new LayoutParams();
                smallWindowParams.type = LayoutParams.TYPE_PHONE;
                smallWindowParams.format = PixelFormat.RGBA_8888;
                smallWindowParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | LayoutParams.FLAG_NOT_FOCUSABLE;
                smallWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                smallWindowParams.width = FloatWindowPetView.viewWidth;
                smallWindowParams.height = FloatWindowPetView.viewHeight;
                smallWindowParams.x = screenWidth;
                smallWindowParams.y = screenHeight / 2;
            }
            smallWindow.setParams(smallWindowParams);
            windowManager.addView(smallWindow, smallWindowParams);
        }
    }

    /**
     * 将小悬浮窗从屏幕上移除。
     *
     * @param context 必须为应用程序的Context.
     */
    public static void removeSmallWindow(Context context) {
        if (smallWindow != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(smallWindow);
            smallWindow = null;
        }
    }

    /**
     * 创建一个大悬浮窗。位置为屏幕正中间偏下。
     *
     * @param context 必须为应用程序的Context.
     */
    public static void createBigWindow(Context context) {
        WindowManager windowManager = getWindowManager(context);
        if (bigWindow == null) {
            bigWindow = new FloatToolbarView(context);
        }
        if (bigWindowParams == null) {
            bigWindowParams = new LayoutParams();
            bigWindowParams.type = LayoutParams.TYPE_PHONE;
            bigWindowParams.format = PixelFormat.RGB_565;
            bigWindowParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL;
            bigWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
            bigWindowParams.width = FloatToolbarView.viewWidth;
            bigWindowParams.height = FloatToolbarView.viewHeight;
        }
        bigWindowParams.x = smallWindowParams.x - FloatWindowPetView.viewWidth;
        bigWindowParams.y = smallWindowParams.y + FloatWindowPetView.viewHeight;
        windowManager.addView(bigWindow, bigWindowParams);
        flag = true;
    }


    /**
     * 创建一个蓝牙链接窗口
     *
     * @param context
     *            必须为应用程序的Context.
     */
    public static void createBluetoothWindow(Context context) {
        WindowManager windowManager = getWindowManager(context);
        if (bigWindow == null) {
            bigWindow = new FloatToolbarView(context);
            if (bigWindowParams == null) {
                bigWindowParams = new LayoutParams();
                bigWindowParams.type = LayoutParams.TYPE_PHONE;
                bigWindowParams.format = PixelFormat.RGBA_8888;
                bigWindowParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL;
                bigWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                bigWindowParams.width = FloatToolbarView.viewWidth;
                bigWindowParams.height = FloatToolbarView.viewHeight;
            }
            bigWindowParams.x = smallWindowParams.x - FloatWindowPetView.viewWidth;
            bigWindowParams.y = smallWindowParams.y + FloatWindowPetView.viewHeight;
            windowManager.addView(bigWindow, bigWindowParams);
            flag = true;
        }
    }

    public static void createBluetoothMessageWindow(Context context) {
        Log.d("myWindowManager", "CreateBluetoothMessage");
        WindowManager windowManager = getWindowManager(context);
        if (bluetoothMessageWindow == null) {
            bluetoothMessageWindow = new bluetoothMessageWindow(context);
            if (bluetoothMessageWindowParams == null) {
                bluetoothMessageWindowParams = new LayoutParams();
                bluetoothMessageWindowParams.type = LayoutParams.TYPE_PHONE;
                bluetoothMessageWindowParams.format = PixelFormat.RGBA_8888;
                bluetoothMessageWindowParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL;
                bluetoothMessageWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                bluetoothMessageWindowParams.width = com.example.desktoppet.bluetoothMessageWindow.viewWidth;
                bluetoothMessageWindowParams.height = com.example.desktoppet.bluetoothMessageWindow.viewHeight;
            }
            bluetoothMessageWindowParams.x = smallWindowParams.x - FloatWindowPetView.viewWidth;
            bluetoothMessageWindowParams.y = (int) (smallWindowParams.y - 1.5 * FloatWindowPetView.viewHeight);

            windowManager.addView(bluetoothMessageWindow, bluetoothMessageWindowParams);
        }
    }

    public static void createBluetoothMessageWindow(Context context, String message) {
        Log.d("myWindowManager", "CreateBluetoothMessage");
        WindowManager windowManager = getWindowManager(context);
        if (bluetoothMessageWindow == null) {
            bluetoothMessageWindow = new bluetoothMessageWindow(context, message);
            if (bluetoothMessageWindowParams == null) {
                bluetoothMessageWindowParams = new LayoutParams();
                bluetoothMessageWindowParams.type = LayoutParams.TYPE_PHONE;
                bluetoothMessageWindowParams.format = PixelFormat.RGBA_8888;
                bluetoothMessageWindowParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL;
                bluetoothMessageWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                bluetoothMessageWindowParams.width = com.example.desktoppet.bluetoothMessageWindow.viewWidth;
                bluetoothMessageWindowParams.height = com.example.desktoppet.bluetoothMessageWindow.viewHeight;
            }
            bluetoothMessageWindowParams.x = smallWindowParams.x - FloatWindowPetView.viewWidth;
            bluetoothMessageWindowParams.y = (int) (smallWindowParams.y - 1.5 * FloatWindowPetView.viewHeight);

            windowManager.addView(bluetoothMessageWindow, bluetoothMessageWindowParams);
        }
    }

    /**
     * 创建一个消息悬浮窗。初始位置为屏幕的右部中间位置。
     *
     * @param context
     *            必须为应用程序的Context.
     */
    public static void createMessageWindow(Context context, PendingIntent pendingIntent, String text) {
        WindowManager windowManager = getWindowManager(context);
        if (messageWindow == null) {
            messageWindow = new FloatWindowMessageView(context, pendingIntent, text);
            if (messageWindowParams == null) {
                messageWindowParams = new LayoutParams();
                messageWindowParams.type = LayoutParams.TYPE_PHONE;
                messageWindowParams.format = PixelFormat.RGBA_8888;
                messageWindowParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | LayoutParams.FLAG_NOT_FOCUSABLE;
                messageWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                messageWindowParams.width = FloatWindowMessageView.viewWidth;
                messageWindowParams.height = FloatWindowMessageView.viewHeight;
            }
            messageWindowParams.x = smallWindowParams.x - FloatWindowPetView.viewWidth;
            messageWindowParams.y = (int) (smallWindowParams.y - 1.5 * FloatWindowPetView.viewHeight);
            windowManager.addView(messageWindow, messageWindowParams);
        }
    }

    public static void createMessageWindow(Context context, String text) {
        WindowManager windowManager = getWindowManager(context);
        if (messageWindow == null) {
            messageWindow = new FloatWindowMessageView(context, null, text);
            if (messageWindowParams == null) {
                messageWindowParams = new LayoutParams();
                messageWindowParams.type = LayoutParams.TYPE_PHONE;
                messageWindowParams.format = PixelFormat.RGBA_8888;
                messageWindowParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | LayoutParams.FLAG_NOT_FOCUSABLE;
                messageWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                messageWindowParams.width = FloatWindowMessageView.viewWidth;
                messageWindowParams.height = FloatWindowMessageView.viewHeight;
            }
            messageWindowParams.x = smallWindowParams.x - FloatWindowPetView.viewWidth;
            messageWindowParams.y = (int) (smallWindowParams.y - 1.5 * FloatWindowPetView.viewHeight);
            windowManager.addView(messageWindow, messageWindowParams);
        }
    }

    /**
     * 进入微信消息页面并移除消息框
     *
     * @param context
     *            必须为应用程序的Context.
     */
    public static void readMessage(Context context, PendingIntent pendingIntent) {
        try {
            pendingIntent.send();
            removeMessageWindow(context);
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将大悬浮窗从屏幕上移除。
     *
     * @param context
     *            必须为应用程序的Context.
     */
    public static void removeBluetoothMessageWindow(Context context) {
        if (bluetoothMessageWindow != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(bluetoothMessageWindow);
            bluetoothMessageWindow = null;
        }
    }

    public static void removeBigWindow(Context context) {
        if (flag) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(bigWindow);
            flag = false;
            //bigWindow = null;
        }
    }


    /**
     * 将消息悬浮窗从屏幕上移除。
     *
     * @param context
     *            必须为应用程序的Context.
     */
    public static void removeMessageWindow(Context context) {
        if (messageWindow != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(messageWindow);
            messageWindow = null;
        }
    }

    /**
     * 是否有悬浮窗(包括小悬浮窗和大悬浮窗)显示在屏幕上。
     *
     * @return 有悬浮窗显示在桌面上返回true，没有的话返回false。
     */
    public static boolean isWindowShowing() {
        return smallWindow != null || flag;
    }

    /**
     * 如果WindowManager还未创建，则创建一个新的WindowManager返回。否则返回当前已创建的WindowManager。
     *
     * @param context
     *            必须为应用程序的Context.
     * @return WindowManager的实例，用于控制在屏幕上添加或移除悬浮窗。
     */
    private static WindowManager getWindowManager(Context context) {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }

    /**
     * 如果ActivityManager还未创建，则创建一个新的ActivityManager返回。否则返回当前已创建的ActivityManager。
     *
     * @param context
     *            可传入应用程序上下文。
     * @return ActivityManager的实例，用于获取手机可用内存。
     */
    private static ActivityManager getActivityManager(Context context) {
        if (mActivityManager == null) {
            mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        }
        return mActivityManager;
    }

    /**
     * 改变宠物模型
     *
     * @param context 必须为应用程序的Context.
     */
    public static void changePetModel(Context context) {
        smallWindow.changePetModel();
    }

    public static void init(Context context) {
        if (bigWindow == null) {
            bigWindow = new FloatToolbarView(context);
            if (bigWindowParams == null) {
                bigWindowParams = new LayoutParams();
                bigWindowParams.type = LayoutParams.TYPE_PHONE;
                bigWindowParams.format = PixelFormat.RGB_565;
                bigWindowParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL;
                bigWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                bigWindowParams.width = FloatToolbarView.viewWidth;
                bigWindowParams.height = FloatToolbarView.viewHeight;
            }
        }
    }

}
