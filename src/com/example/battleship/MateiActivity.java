package com.example.battleship;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MateiActivity extends ListActivity {
	static BluetoothAdapter mBluetoothAdapter = null;
	private ArrayAdapter<String> mPairedDevicesArrayAdapter;
	Set<BluetoothDevice> pairedDevices;
	boolean succesfullConnection = false;
	BluetoothDevice[] pairedDevicesArray;
//	static UUID theUUID = UUID.fromString("db73afa1-2f9e-4536-8358-3c2358b9993c");
	static UUID theUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	static boolean startNewActivity = false;
	static AlertDialog.Builder alert;
	static AcceptThread acceptThread;
	static ConnectThread connectThread;
	static ConnectedThread connectedThread;
	static InputStream mmInStream = null;
    static OutputStream mmOutStream = null;
    boolean connectThredCalled = false;
    static MateiActivity main;
    static Context context;
    static Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		main = this;
		context = getApplicationContext();
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		setListAdapter(mPairedDevicesArrayAdapter);
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
			// no bluetooth adapter
			finish();
		}
		
		if (!mBluetoothAdapter.isEnabled()) {
		    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    startActivity(enableBtIntent);
		}
		
		while (!mBluetoothAdapter.isEnabled()) {}
		
		pairedDevices = mBluetoothAdapter.getBondedDevices();
		pairedDevicesArray = new BluetoothDevice[pairedDevices.size()];
		int iterator = 0;
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                pairedDevicesArray[iterator++] = device;
            }
        } else {
            mPairedDevicesArrayAdapter.add("No paired devices");
        }
        
        acceptThread = new AcceptThread();
        acceptThread.start();
        
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		if(succesfullConnection == false) {
			connectThread = new ConnectThread(pairedDevicesArray[position], this);
			connectThread.start();
			connectThredCalled = true;
		}	
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		acceptThread.cancel();
		if(connectThredCalled == true) connectThread.cancel();
	}

}
