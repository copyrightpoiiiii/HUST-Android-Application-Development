package com.example.desktoppet;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class FloatWindowPetView extends LinearLayout {

    public static int viewWidth;

    public static int viewHeight;
    public static ImageView petView;
    public static boolean flag = true;
    private static int statusBarHeight;
    private static int petIndex = 2;
    private WindowManager windowManager;
    private WindowManager.LayoutParams params;
    private float xInScreen;
    private float yInScreen;
    private float xDownInScreen;
    private float yDownInScreen;
    private float xInView;
    private float yInView;
    private float swidth;
    private View view;
    private boolean isOpenBigWin = false;
    private int[] petStayModelID = {
            R.drawable.cat_stay,
            R.drawable.pika_stay,
            R.drawable.sponge_stay,
            R.drawable.dog_stay
    };
    private int[] petRunModelID = {
            R.drawable.cat_run,
            R.drawable.pika_run,
            R.drawable.sponge_run,
            R.drawable.dog_run
    };
    private int peyNum = 4;

    public FloatWindowPetView(final Context context) {
        super(context);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater.from(context).inflate(R.layout.float_window_small, this);
        view = findViewById(R.id.small_window_layout);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;
        petView = findViewById(R.id.pet);
        petView.setBackgroundResource(petStayModelID[petIndex]);
        new NormalTask().execute();
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        swidth = outMetrics.widthPixels;
        myWindowManager.init(getContext());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!flag) {
                    TranslateAnimation tAnim = getTranslateAnimation(0, false);
                    view.startAnimation(tAnim);
                    flag = true;
                }
                petView.setBackgroundResource(petRunModelID[petIndex]);
                ((AnimationDrawable) petView.getBackground()).start();
                String stat;
                if (((AnimationDrawable) petView.getBackground()).isRunning())
                    stat = "isRunning";
                else stat = "notRunning";
                Log.d("1", stat);
                xInView = event.getX();
                yInView = event.getY();
                xDownInScreen = event.getRawX();
                yDownInScreen = event.getRawY() - getStatusBarHeight();
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStatusBarHeight();
                break;
            case MotionEvent.ACTION_MOVE:
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() = getStatusBarHeight();
                updateViewPosition();
            case MotionEvent.ACTION_UP:
                if (xDownInScreen == xInScreen && yDownInScreen == yInScreen) {
                    if (!isOpenBigWin) {
                        openBigWindow();
                        isOpenBigWin = true;
                    } else {
                        closeBigWindow();
                        isOpenBigWin = false;
                    }
                } else {
                    if (isOpenBigWin) {
                        closeBigWindow();
                        isOpenBigWin = false;
                    }
                    updateViewPosition3();
                }
                petView.setBackgroundResource(petStayModelID[petIndex]);
                break;
            default:
                break;
        }
        return true;
    }


}