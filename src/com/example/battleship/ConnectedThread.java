package com.example.battleship;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class ConnectedThread extends Thread{
	BluetoothSocket theSocket = null;
	InputStream mmInStream;
    OutputStream mmOutStream;
    MateiActivity mainActivity;
    GameActivity playground;
    
    public void setPlayground(GameActivity play) {
    	playground = play;
    }
	
	public ConnectedThread(BluetoothSocket socket, MateiActivity main) {
		theSocket = socket;
		mainActivity = main;
	}
	
	@Override
	public void run() {
		Log.e("+++ MATEI +++", "Connected thread is now running");
		
		InputStream tmpIn = null;
        OutputStream tmpOut = null;

        // Get the BluetoothSocket input and output streams
        try {
            tmpIn = theSocket.getInputStream();
            tmpOut = theSocket.getOutputStream();
            Log.e("+++ MATEI", "GOT input and output");
        } catch (IOException e) {
            Log.e("+++ MATEI", "NO input and output");
        }
        
        mainActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent("android.intent.action.WELCOME");
				mainActivity.startActivity(intent);
			}
		});

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
        
        // Keep listening to the InputStream while connected
        while (true) {
            try {
            	Log.e("+++ MATEI", "Begin reading");
            	byte[] buffer = new byte[1024];
                int bytes;
                bytes = mmInStream.read(buffer);
                String toWrite = new String(buffer).substring(0, bytes);
                Log.e("+++ MATEI", "Got read: " + toWrite);
                int received = Integer.parseInt(toWrite);
                final int selectHeap = received / 10;
                final int selectSticks = received % 10;
                playground.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						/*playground.doWork();
						playground.nim._board[selectHeap - 1] -= selectSticks;
						playground.printHeapState();
						playground.zeroOrHero();
						playground.friendLastMove(selectSticks, selectHeap);*/
					}
				});
            } catch (IOException e) {
            	Log.e("+++ MATEI", "Problem while reading");
                break;
            }
        }
		return;
	}
	
	public void write(String toWrite) {
		byte[] buffer = new byte[toWrite.getBytes().length];
		buffer = toWrite.getBytes();
        
        try {
        	Log.e("+++ MATEI", "Begin writing");
			mmOutStream.write(buffer);
			Log.e("+++ MATEI", "We got write");
		} catch (IOException e1) {
			Log.e("+++ MATEI", "Problem while writing");
		}
	}

}
