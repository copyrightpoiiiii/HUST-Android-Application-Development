package com.example.desktoppet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.lang.reflect.Field;

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
    private int petNum = 4;

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
                yInScreen = event.getRawY() - getStatusBarHeight();
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

    public static int getPetIndex() {
        return petIndex;
    }

    public void setParams(WindowManager.LayoutParams wParams) {
        params = wParams;
    }

    private void updateViewPosition() {
        params.x = (int) (xInScreen - xInView);
        params.y = (int) (yInScreen - yInView);
        windowManager.updateViewLayout(this, params);
    }

    private void updateViewPosition3() {
        if (params.x <= 0) {
            doTranslateAnimation(-1);
            flag = false;
        } else if (params.x + viewWidth >= swidth) {
            doTranslateAnimation(1);
            flag = false;
        }
    }

    private TranslateAnimation getTranslateAnimation(float x, boolean FillAfter) {
        TranslateAnimation tAnim = new TranslateAnimation(0, x, 0, 0);
        tAnim.setDuration(500);
        tAnim.setFillAfter(FillAfter);
        tAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        return tAnim;
    }

    private void doTranslateAnimation(final float x) {
        AnimationSet set = new AnimationSet(true);
        flag = false;
        TranslateAnimation outAnim = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_PARENT, x,
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0);
        outAnim.setDuration(500);
        outAnim.setStartOffset(100);
        TranslateAnimation readyAnim2 = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0f,
                TranslateAnimation.RELATIVE_TO_SELF, -x / 2f,
                TranslateAnimation.RELATIVE_TO_SELF, 0f,
                TranslateAnimation.RELATIVE_TO_SELF, 0f
        );
        readyAnim2.setDuration(300);
        readyAnim2.setStartOffset(550);
        set.addAnimation(readyAnim2);
        set.setFillAfter(true);
        outAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            /*
             * 设置贴屏换图
             */
            @SuppressLint("NewApi")
            @Override
            public void onAnimationEnd(Animation animation) {
                petView.setScaleX(x);  //设置镜像
                petView.setBackground(getResources().getDrawable(R.drawable.lovely_info_win));

            }
        });
        set.addAnimation(outAnim);
        view.startAnimation(set);
    }

    private void openBigWindow() {
        myWindowManager.createBigWindow(getContext());
    }

    private void openBluetooth() {
        myWindowManager.createBluetoothMessageWindow(getContext());
    }

    private void closeBigWindow() {
        myWindowManager.removeBigWindow(getContext());
    }

    private void closeBluetooth() {
        myWindowManager.removeBluetoothMessageWindow(getContext());
    }

    private int getStatusBarHeight() {
        if (statusBarHeight == 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer) field.get(o);
                statusBarHeight = getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }

    public void changePetModel() {
        petIndex = (petIndex + 1) % petNum;
        petView.setBackgroundResource(petStayModelID[petIndex]);
        ((AnimationDrawable) petView.getBackground()).start();
    }

}