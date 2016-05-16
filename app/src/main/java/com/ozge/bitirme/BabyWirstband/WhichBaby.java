package com.ozge.bitirme.BabyWirstband;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by TUBA on 15.05.2016.
 */
public class WhichBaby  extends AppCompatActivity {
    DatabaseBabyData BabyInformtion;
    TextView listText;
    ListView lv;
    public String text;
    public static String veri=null;
    public static final String EXTRA_BLE_DEVICE= "com.mbientlab.bletoolbox.examples.WhichBaby.EXTRA_BLE_DEVICE";
    private final static int REQUEST_ENABLE_BT= 0, SCAN_DEVICE=1;
    public static String macadress;
    MeasurementActivity mobject= new MeasurementActivity();

    public BluetoothAdapter btAdapter;
    public static BluetoothDevice device;
    public TextView textdnm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wichbabylist);
        BabyInformtion =new DatabaseBabyData(this);
            SQLiteDatabase db = BabyInformtion.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM BabyInformation", null);
        lv = (ListView) findViewById(R.id.listBaby);
        ArrayList<BabyItem> list = new ArrayList<BabyItem>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String ad = cursor.getString((cursor.getColumnIndex("name")));
            String soyad = cursor.getString((cursor.getColumnIndex("surname")));
            String birthday = cursor.getString((cursor.getColumnIndex("birthday")));
            int gender = cursor.getInt((cursor.getColumnIndex("gender")));
            BabyItem nesne = new BabyItem(gender, ad, soyad, birthday);
            list.add(nesne);


        }
        BabyListAdapter adp = new BabyListAdapter(this, list);
        lv.setAdapter(adp);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent bleScanIntent = new Intent(WhichBaby.this, ScannerActivity.class);
                startActivityForResult(bleScanIntent, SCAN_DEVICE);
            }
        });

      textdnm=(TextView)findViewById(R.id.textView2);
        btAdapter= ((BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter();
        if (btAdapter == null) {
            new AlertDialog.Builder(this).setTitle(R.string.error_title)
                    .setMessage(R.string.error_no_bluetooth)
                    .setCancelable(false)
                    .setPositiveButton(R.string.label_ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            WhichBaby.this.finish();
                        }
                    })
                    .create()
                    .show();
        }
        else if (!btAdapter.isEnabled()) {
            final Intent enableIntent= new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);

        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_CANCELED) {
                    finish();
                }
                break;
            case SCAN_DEVICE:
                if (data != null) {
                    device = data.getParcelableExtra(WhichBaby.EXTRA_BLE_DEVICE);
                    macadress=device.getAddress();
                    Toast.makeText(this, "Device selected: " + device.getAddress(), Toast.LENGTH_LONG).show();
                    textdnm.setText(macadress);
                    mobject.mydevice=device;
                }
                Intent intent = new Intent(this, MeasurementActivity.class);
                startActivity(intent);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
   private void KayitGoster(Cursor cursor) {


   }











}