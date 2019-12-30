package com.example.desktoppet;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.AlarmClock;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class FloatToolbarView extends LinearLayout {
    public static int viewWidth;

    public static int viewHeight;

    public FloatToolbarView(final Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.float_window_big, this);
        View view = findViewById(R.id.big_window_layout);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;
        Button home = findViewById(R.id.home);
        Button clock = findViewById(R.id.clock);
        Button bluetooth = findViewById(R.id.bluetooth);
        Button set = findViewById(R.id.set);
        Button next = findViewById(R.id.next);
        Button sear = findViewById(R.id.search);

        home.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent petInfo = new Intent(context, ShowPetInfoActivity.class);
                petInfo.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(petInfo);
            }
        });

        clock.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent alarm = new Intent(AlarmClock.ACTION_SET_ALARM);
                alarm.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(alarm);
            }
        });

        bluetooth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bluetooth = new Intent(context, ModeActivity.class);
                bluetooth.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(bluetooth);
            }
        });

        set.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settings = new Intent(context, Setting.class);
                settings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(settings);
            }
        });

        next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                myWindowManager.changePetModel(context);
            }
        });

        sear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.baidu.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });

    }
}
