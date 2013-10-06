package com.example.battleship;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.widget.Toast;

public class AcceptThread extends Thread {
    private final BluetoothServerSocket mmServerSocket;
    BluetoothSocket socket = null;
    InputStream mmInStream = null;
    OutputStream mmOutStream = null;
 
    public AcceptThread() {
        // Use a temporary object that is later assigned to mmServerSocket,
        // because mmServerSocket is final
        BluetoothServerSocket tmp = null;
        try {
            // MY_UUID is the app's UUID string, also used by the client code
            tmp = MateiActivity.mBluetoothAdapter.listenUsingRfcommWithServiceRecord("Reborn", MateiActivity.theUUID);
        } catch (IOException e) { }
        mmServerSocket = tmp;
        Log.e("+++ MATEI +++", "LISTEN Got the Socket");
    }
 
    public void run() {
        // Keep listening until exception occurs or a socket is returned
        while (true) {
            try {
            	Log.e("+++ MATEI +++", "Start Listening");
                socket = mmServerSocket.accept();
                Log.e("+++ MATEI +++", "Connection ACCEPTED");
                GameActivity.friend = socket.getRemoteDevice().getName();
                GameActivity.cnt = 1;
            } catch (IOException e) {
            	Log.e("+++ MATEI +++", "Connection NOT accepted");
                break;
            }
            // If a connection was accepted
            if (socket != null) {
                // Do work to manage the connection (in a separate thread)
            	
            //  manageConnectedSocket(socket);
            	Log.e("+++ MATEI +++", "HOT HOT HOT");
            	
            	
            	ConnectedThread connectedThread = new ConnectedThread(socket, MateiActivity.main);
            	MateiActivity.connectedThread = connectedThread;
            	connectedThread.start();
            	
                try {
					mmServerSocket.close();
					Log.e("+++ MATEI +++", "ServerSocket closed");
				} catch (IOException e) {
					e.printStackTrace();
				}
                break;
            }
        }
    }
 
    /** Will cancel the listening socket, and cause the thread to finish */
    public void cancel() {
        try {
        	if(socket != null) socket.close();
            mmServerSocket.close();
            Log.e("+++ MATEI +++", "AcceptThread cancel called");
        } catch (IOException e) { }
    }
}
