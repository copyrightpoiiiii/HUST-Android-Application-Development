package com.example.desktoppet;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class ModeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myBluetoothManager.setContext(this);
        if (!myBluetoothManager.isAvailable()) {
            Toast.makeText(this, "Device does not support BlueTooth", Toast.LENGTH_LONG).show();
            this.finish();
        } else if (!myBluetoothManager.isEnable()) {
            myBluetoothManager.enable();
            switchMode();
        } else switchMode();
    }

    private void switchMode() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("switch mode");
        builder.setNegativeButton("waiting connect", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!myBluetoothManager.isScanMode()) {
                    String s = BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE;
                    startActivityForResult(new Intent(s), bluetoothStatus.REQUEST_DISCOVERABLE);
                } else
                    Toast.makeText(ModeActivity.this, "Wait for connect...", Toast.LENGTH_LONG).show();
            }
        });
        builder.setPositiveButton("search device", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                startActivityForResult(intent, bluetoothStatus.REQUEST_CONNECT_DEVICE);
            }
        });
        builder.create().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == bluetoothStatus.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                switchMode();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "bluetooth is disable", Toast.LENGTH_LONG).show();
                this.finish();
            }
        } else if (requestCode == bluetoothStatus.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK) {
                Intent intent = new Intent(ModeActivity.this, myBluetoothService.class);
                intent.putExtra("ListenOrSearch", false);
                String s = data.getExtras().getString(bluetoothStatus.DEVICE_ADDRESS);
                intent.putExtra(bluetoothStatus.DEVICE_ADDRESS, s);
                this.startService(intent);
                ModeActivity.this.finish();
            }
        } else if (requestCode == bluetoothStatus.REQUEST_DISCOVERABLE) {
            Intent i = new Intent(ModeActivity.this, myBluetoothService.class);
            i.putExtra("ListenOrSearch", true);
            startService(i);
            ModeActivity.this.finish();
        }
    }
}
