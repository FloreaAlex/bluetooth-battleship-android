package com.example.battleship;

import java.io.IOException;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.widget.Toast;

public class ConnectThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    MateiActivity mainActivity;
    
    public ConnectThread(BluetoothDevice device, MateiActivity main) {
    	mainActivity = main;
        // Use a temporary object that is later assigned to mmSocket,
        // because mmSocket is final
        BluetoothSocket tmp = null;
        mmDevice = device;
 
        // Get a BluetoothSocket to connect with the given BluetoothDevice
        try {
            // MY_UUID is the app's UUID string, also used by the server code
            tmp = device.createRfcommSocketToServiceRecord(MateiActivity.theUUID);
        } catch (IOException e) { }
        mmSocket = tmp;
        Log.e("+++ MATEI +++", "Got the socket");
    }
 
    public void run() {
        // Cancel discovery because it will slow down the connection
        MateiActivity.mBluetoothAdapter.cancelDiscovery();
 
        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
        	Log.e("+++ MATEI +++", "Connection BEGIN");
            mmSocket.connect();
            Log.e("+++ MATEI +++", "Connection SUCCESS");
            GameActivity.friend = mmDevice.getName();
            GameActivity.cnt = 0;
            ConnectedThread connectedThread = new ConnectedThread(mmSocket, MateiActivity.main);
            MateiActivity.connectedThread = connectedThread;
        	connectedThread.start();
        	
        } catch (IOException connectException) {
            // Unable to connect; close the socket and get out
            try {
                mmSocket.close();
            } catch (IOException closeException) { }
            Log.e("+++ MATEI +++", "Conection FAILED: " + connectException.toString());
            mainActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(mainActivity, "Try to connect from the other device", Toast.LENGTH_LONG).show();
				}
			});
            return;
        }
 
        // Do work to manage the connection (in a separate thread)
    //  manageConnectedSocket(mmSocket);
    }
 
    /** Will cancel an in-progress connection, and close the socket */
    public void cancel() {
        try {
            mmSocket.close();
            Log.e("+++ MATEI +++", "ConnectThread cancel called");
        } catch (IOException e) { }
    }
}
