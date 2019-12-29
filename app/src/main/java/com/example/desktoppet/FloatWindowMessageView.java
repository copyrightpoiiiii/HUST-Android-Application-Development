package com.example.desktoppet;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.AlarmClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FloatWindowMessageView extends LinearLayout {
    public static int viewWidth;

    public static int viewHeight;

    public static AccessibilityEvent myEvent;

    public FloatWindowMessageView(final Context context, final PendingIntent pendingIntent, final String text) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.float_window_message, this);
        View view = findViewById(R.id.message_window_layout);
        TextView textView = view.findViewById(R.id.inform_text);
        textView.setText(text);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;
        Button ok = findViewById(R.id.inform_ok);
        Button cancel = findViewById(R.id.inform_cancel);

        ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                myWindowManager.readMessage(context, pendingIntent);
            }
        });

        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                myWindowManager.removeMessageWindow(context);
            }
        });
    }
}
