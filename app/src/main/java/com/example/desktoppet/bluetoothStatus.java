package com.example.desktoppet;

public class bluetoothStatus {
    // Constants that indicate the current connection state
    public static final int STATE_NONE = 0;       	// doing nothing
    public static final int STATE_LISTEN = 1;     	// listening for incoming connections
    public static final int STATE_CONNECTING = 2; 	// initiating an outgoing connection
    public static final int STATE_CONNECTED = 3;  	// connected to a remote device
    public static final int STATE_NULL = -1;  	 	// service is null

    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Intent request codes
    public static final int REQUEST_CONNECT_DEVICE = 384;
    public static final int REQUEST_ENABLE_BT = 385;
    public static final int REQUEST_DISCOVERABLE = 386;


    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String DEVICE_ADDRESS = "device_address";
    public static final String TOAST = "toast";

    public static final boolean DEVICE_ANDROID = true;
    public static final boolean DEVICE_OTHER = false;

    // Return Intent extra
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
}
