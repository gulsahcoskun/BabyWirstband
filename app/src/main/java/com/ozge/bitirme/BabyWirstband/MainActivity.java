/*
 * Copyright 2015 MbientLab Inc. All rights reserved.
 */

package com.ozge.bitirme.BabyWirstband;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mbientlab.metawear.UnsupportedModuleException;
import com.mbientlab.metawear.module.Debug;


public class MainActivity extends AppCompatActivity   {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
  /*  @Override
 public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.action_connect) {

            Intent bleScanIntent = new Intent(this, ScannerActivity.class);
            startActivityForResult(bleScanIntent, SCAN_DEVICE);
            }

        else if (id == R.id.action_clear_log) {

            }
        else if (id == R.id.action_reset_device) {

        }
        //geri buttonu
        if(item.getItemId()==android.R.id.home){
            Intent intent = new Intent(getApplicationContext(),MeasurementActivity.class);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
    public void goAppInformation(View view){
     Intent intent = new Intent();
     intent.setClass(getApplicationContext(), InformationApp.class);
     startActivity(intent);

 }
    public  void goBabyInformation(View view){
     Intent intent = new Intent();
     intent.setClass(getApplicationContext(), InformationBaby.class);
     startActivity(intent);

    }
    public  void goHistory(View view){
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(),WhichBaby.class);
        startActivity(intent);

    }
    public  void goConnect(View view){
            Toast.makeText(this, "Baby selected pleas ", Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(),WhichBaby.class);
            startActivity(intent);
    }
    public  void goWhichBaby(View view){
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), WhichBaby.class);
            startActivity(intent);
    }

    /*
    @Override
    public void onDestroy() {
        super.onDestroy();

        ///< Unbind the service when the activity is destroyed
        getApplicationContext().unbindService(this);
    }



    @Override

    public void onServiceConnected (ComponentName name, IBinder service)  {
        MetaWearBleService.LocalBinder binder = (MetaWearBleService.LocalBinder) service;
        final BluetoothManager btManager=
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
       BluetoothDevice remoteDevice= MainActivity.device;

        binder.executeOnUiThread();
        // Create a MetaWear board object for the Bluetooth Device
        mwBoard= binder.getMetaWearBoard(remoteDevice);

        mwBoard.setConnectionStateHandler(new MetaWearBoard.ConnectionStateHandler() {
            @Override
            public void connected() {
                // pDialog.hide();
                Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_LONG).show();
                Log.i("test", "Connected");
                Log.i("test", "MetaBoot? " + mwBoard.inMetaBootMode());

                mwBoard.readDeviceInformation().onComplete(new AsyncOperation.CompletionHandler<MetaWearBoard.DeviceInformation>() {
                    @Override
                    public void success(MetaWearBoard.DeviceInformation result) {
                        Log.i("test", "Device Information: " + result.toString());

                    }

                    @Override
                    public void failure(Throwable error) {
                        Log.e("test", "Error reading device information", error);
                    }
                });

            }

            @Override
            public void disconnected() {
                // pDialog.hide();
                Toast.makeText(MainActivity.this, "Disconnected", Toast.LENGTH_LONG).show();
                Log.i("test", "Disconnected");

            }

        });


    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }


   public void connectMe(View v) {
        mwBoard.connect();}

*/


}
