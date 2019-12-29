package com.example.desktoppet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class bluetoothMessageWindow extends LinearLayout {

    //width of message window
    public static int viewWidth;

    //height of window
    public static int viewHeight;

    EditText edit;

    TextView text;

    TextView quit;

    boolean touched;

    public bluetoothMessageWindow(final Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.text_dialog, this);
        View view = findViewById(R.id.bluetooth_message);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;

        edit = findViewById(R.id.input_text);
        text = findViewById(R.id.send);
        quit = findViewById(R.id.quit);
        text.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = edit.getText().toString();
                edit.setText("");
                myBluetoothManager.bt.send(str, true);
                str = "";
                closeBluetooth(context);
            }
        });

        quit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeBluetooth(context);
            }
        });
    }

    public bluetoothMessageWindow(final Context context, String message) {
        super(context);
        touched = false;
        LayoutInflater.from(context).inflate(R.layout.text_dialog, this);
        View view = findViewById(R.id.bluetooth_message);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;
        quit = findViewById(R.id.quit);
        edit = findViewById(R.id.input_text);
        text = findViewById(R.id.send);
        message = "[Bluetooth Meaasge]" + message;
        edit.setText(message);

        text.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (touched) {
                    String str = edit.getText().toString();
                    edit.setText("");
                    myBluetoothManager.bt.send(str, true);
                    str = "";
                    closeBluetooth(context);
                } else {
                    Toast.makeText(context, "send failure, no input ",
                            Toast.LENGTH_SHORT).show();
                }
            }

        });

        edit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (!touched) {
                    touched = true;
                    edit.setText("");
                }
            }

        });

        quit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                closeBluetooth(context);
            }

        });

    }

    private static void closeBluetooth(Context context) {
        myWindowManager.removeBluetoothMessageWindow(context);
    }
}
