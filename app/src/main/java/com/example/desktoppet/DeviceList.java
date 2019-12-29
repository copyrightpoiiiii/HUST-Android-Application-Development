package com.example.desktoppet;

import android.app.Activity;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.*;

@SuppressLint("NewApi")
public class DeviceList extends Activity {

    //debug
    private static final String tag = "bluetooth SSP";
    private static final boolean D = true;

    //member fields
    private BluetoothAdapter mBtAdapter;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    private Set<BluetoothDevice> pairedDevices;
    private Button scanButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setup window
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        int listId = getIntent().getIntExtra("layout_list", R.layout.device_list);
        setContentView(listId);

        String strBluetoothDevices = getIntent().getStringExtra("bluetooth_devices");
        if (strBluetoothDevices == null)
            strBluetoothDevices = "Bluetooth Devices";
        setTitle(strBluetoothDevices);

        //set result:canceled in case that usr backs out
        setResult(Activity.RESULT_CANCELED);

        //init button to perform device discovery
        scanButton = (Button) findViewById(R.id.button_scan);
        String strScanDevice = getIntent().getStringExtra("scan_for_device");
        if (strScanDevice == null)
            strScanDevice = "SCAN FOR DEVICE";
        scanButton.setText(strScanDevice);
        scanButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                doDiscovery();
            }
        });

        //init 2 array adapter, for paired and newly discovered devices
        int layout_text = getIntent().getIntExtra("layout_text", R.layout.device_name);
        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, layout_text);

        //find and setup the list view for paired devices
        ListView pairedListView = (ListView) findViewById(R.id.list_devices);
        pairedListView.setAdapter(mPairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(mDeviceClickListener);

        //register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);

        //register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);

        //get local bt adapter
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        //get set of currently paired devices
        pairedDevices = mBtAdapter.getBondedDevices();

        //add all paired device to thr ArrayAdapter, if there're any
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        } else {
            String noDevice = "no device found";
            mPairedDevicesArrayAdapter.add(noDevice);
        }
    }

    protected void onDestory(){
        super.onDestroy();

        //ensure doing no more discovery
        if(mBtAdapter!=null){
            mBtAdapter.cancelDiscovery();
        }

        //unregister broadcast listeners
        this.unregisterReceiver(mReceiver);
        this.finish();
    }

    private void doDiscovery(){
        if(D) Log.d(tag, "doDiscovery()");

        //Remove all elems from list
        mPairedDevicesArrayAdapter.clear();

        // If there are paired devices, add each one to the ArrayAdapter
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        } else {
            String strNoFound = getIntent().getStringExtra("no_devices_found");
            if(strNoFound == null)
                strNoFound = "No devices found";
            mPairedDevicesArrayAdapter.add(strNoFound);
        }

        //indicate scanning in the title
        String strScanning = getIntent().getStringExtra("scanning");
        if(strScanning == null)
            strScanning = "Scanning for devices...";
        setProgressBarIndeterminateVisibility(true);
        setTitle(strScanning);

        // Turn on sub-title for new devices
        // findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);
        // If we're already discovering, stop it
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }

        // Request discover from BluetoothAdapter
        mBtAdapter.startDiscovery();
    }

    //The on-click listener for all devices i the ListView
    private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            // Cancel discovery because it's costly and we're about to connect
            if(mBtAdapter.isDiscovering())
                mBtAdapter.cancelDiscovery();

            String strNoFound = getIntent().getStringExtra("no_devices_found");
            if(strNoFound == null)
                strNoFound = "No devices found";
            if(!((TextView) v).getText().toString().equals(strNoFound)) {
                // Get the device MAC address, which is the last 17 chars in the View
                String info = ((TextView) v).getText().toString();
                String address = info.substring(info.length() - 17);

                // Create the result Intent and include the MAC address
                Intent intent = new Intent();
                intent.putExtra(bluetoothStatus.EXTRA_DEVICE_ADDRESS, address);

                // Set result and finish this Activity
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        }
    };

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                // If it's already paired, skip it, because it's been listed already
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    String strNoFound = getIntent().getStringExtra("no_devices_found");
                    if(strNoFound == null)
                        strNoFound = "No devices found";

                    if(mPairedDevicesArrayAdapter.getItem(0).equals(strNoFound)) {
                        mPairedDevicesArrayAdapter.remove(strNoFound);
                    }
                    mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }

                // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                setProgressBarIndeterminateVisibility(false);
                String strSelectDevice = getIntent().getStringExtra("select_device");
                if(strSelectDevice == null)
                    strSelectDevice = "Select a device to connect";
                setTitle(strSelectDevice);
            }
        }
    };
}
