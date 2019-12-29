package com.example.desktoppet;


import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

public class MyWindowManager {

    private static FloatPetView smallWindow;

    private static FloatToolbarView bigWindow;

    private static LayoutParams smallWindowParams;

    private static FloatMessageView messageWindow;

    private static BluetoothMessageWindow bluetoothMessageWindow;

    private static LayoutParams bluetoothMessageWindowParams;

    private static LayoutParams bigWindowParams;

    private static LayoutParams messageWindowParams;

    private static WindowManager mWindowManager;

    private static ActivityManager mActivityManager;

    private static boolean flag = false;

    public static void createSmallWindow(Context context) {
        WindowManager windowManager = getWindowManager(context);
        if (smallWindow == null)
            smallWindow = new FloatToolbarView(context);
        if (smallWindowParams == null) {
            smallWindowParams = new LayoutParams();
            smallWindowParams.type = LayoutParams.TYPE_PHONE;
            smallWindowParams.format = PixelFormat.RGBA_8888;
            smallWindowParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL;
            smallWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
            smallWindowParams.width = FloatToolbarView.viewWidth;
            smallWindowParams.height = FloatToolbarView.viewHeight;
        }
        smallWindowParams.x = smallWindowParams.x - FloatPetView.viewWidth;
        smallWindowParams.y = smallWindowParams.y - FloatPetView.viewHeight;
        windowManager.addView(smallWindow, smallWindowParams);
        flag = true;
    }

    public static void createBigWindow(Context context) {
        WindowManager windowManager = getWindowManager(context);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
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
        bigWindowParams.x = smallWindowParams.x - FloatPetView.viewWidth;
        bigWindowParams.y = smallWindowParams.y + FloatPetView.viewHeight;
        windowManager.addView(bigWindow, bigWindowParams);
        flag = true;
    }

    public static void createBluetoothMessageWindow(Context context) {
        Log.d("MyWindowManager", "CreateBluetoothMessage");
        WindowManager windowManager = getWindowManager(context);
        if (bluetoothMessageWindow == null) {
            bluetoothMessageWindow = new BluetoothMessageWindow(context);
            if (bluetoothMessageWindowParams == null) {
                bluetoothMessageWindowParams = new LayoutParams();
                bluetoothMessageWindowParams.type = LayoutParams.TYPE_PHONE;
                bluetoothMessageWindowParams.format = PixelFormat.RGBA_8888;
                bluetoothMessageWindowParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL;
                bluetoothMessageWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                bluetoothMessageWindowParams.width = BluetoothMessageWindow.viewWidth;
                bluetoothMessageWindowParams.height = BluetoothMessageWindow.viewHeight;
            }
            bluetoothMessageWindowParams.x = smallWindowParams.x - FloatWindowPetView.viewWidth;
            bluetoothMessageWindowParams.y = (int) (smallWindowParams.y - 1.5 * FloatWindowPetView.viewHeight);

            windowManager.addView(bluetoothMessageWindow, bluetoothMessageWindowParams);
        }
    }

    public static void createBluetoothMessageWindow(Context context, String message) {
        Log.d("MyWindowManager", "CreateBluetoothMessage");
        WindowManager windowManager = getWindowManager(context);
        if (bluetoothMessageWindow == null) {
            bluetoothMessageWindow = new BluetoothMessageWindow(context, message);
            if (bluetoothMessageWindowParams == null) {
                bluetoothMessageWindowParams = new LayoutParams();
                bluetoothMessageWindowParams.type = LayoutParams.TYPE_PHONE;
                bluetoothMessageWindowParams.format = PixelFormat.RGBA_8888;
                bluetoothMessageWindowParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL;
                bluetoothMessageWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                bluetoothMessageWindowParams.width = BluetoothMessageWindow.viewWidth;
                bluetoothMessageWindowParams.height = BluetoothMessageWindow.viewHeight;
            }
            bluetoothMessageWindowParams.x = smallWindowParams.x - FloatWindowPetView.viewWidth;
            bluetoothMessageWindowParams.y = (int) (smallWindowParams.y - 1.5 * FloatWindowPetView.viewHeight);

            windowManager.addView(bluetoothMessageWindow, bluetoothMessageWindowParams);
        }
    }

    public static void createMessageWindow(Context context, PendingIntent pendingIntent, String text) {
        WindowManager windowManager = getWindowManager(context);
        if (messageWindow == null) {
            messageWindow = new FloatMessageView(context, pendingIntent, text);
            if (messageWindowParams == null) {
                messageWindowParams = new LayoutParams();
                messageWindowParams.type = LayoutParams.TYPE_PHONE;
                messageWindowParams.format = PixelFormat.RGBA_8888;
                messageWindowParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | LayoutParams.FLAG_NOT_FOCUSABLE;
                messageWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                messageWindowParams.width = FloatMessageView.viewWidth;
                messageWindowParams.height = FloatMessageView.viewHeight;
            }
            messageWindowParams.x = smallWindowParams.x - FloatPetView.viewWidth;
            messageWindowParams.y = (int) (smallWindowParams.y - 1.5 * FloatPetView.viewHeight);
            windowManager.addView(messageWindow, messageWindowParams);
        }
    }


    public static void readMessage(Context context, PendingIntent pendingIntent) {
        try {
            pendingIntent.send();
            removeMessageWindow(context);
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    public static void removeSmallWindow(Context context) {
        if (smallWindow != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(smallWindow);
            smallWindow = null;
        }
    }

    public static void removeBluetoothMessaheWindow(Context context) {
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
        }
    }

    public static void removeMessageWindow(Context context) {
        if (messageWindow != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(messageWindow);
            messageWindow = null;
        }
    }

    public static boolean isWindowShowing() {
        return smallWindow != null || flag;
    }

    private static WindowManager getWindowManager(Context context) {
        if (mWindowManager == null)
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return mWindowManager;
    }

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
