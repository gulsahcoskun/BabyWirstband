package com.ozge.bitirme.BabyWirstband;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mbientlab.metawear.AsyncOperation;
import com.mbientlab.metawear.Message;
import com.mbientlab.metawear.MetaWearBleService;
import com.mbientlab.metawear.MetaWearBoard;
import com.mbientlab.metawear.RouteManager;
import com.mbientlab.metawear.UnsupportedModuleException;
import com.mbientlab.metawear.data.CartesianFloat;
import com.mbientlab.metawear.data.CartesianShort;
import com.mbientlab.metawear.module.Bmi160Gyro;
import com.mbientlab.metawear.module.Settings;
import com.mbientlab.metawear.module.Temperature;
import com.mbientlab.metawear.module.Timer;
import com.mbientlab.metawear.processor.Maths;
import com.mbientlab.metawear.module.Bmi160Accelerometer;
import com.mbientlab.metawear.module.Debug;
import com.mbientlab.metawear.module.Logging;
import com.mbientlab.metawear.module.Temperature;
import com.mbientlab.metawear.module.Timer;
import com.mbientlab.metawear.processor.Maths;
import com.mbientlab.metawear.module.Accelerometer;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;
import java.util.TimerTask;

public class MeasurementActivity extends AppCompatActivity implements ServiceConnection {
    public static final String EXTRA_BLE_DEVICE = "com.mbientlab.bletoolbox.examples.MainActivity.EXTRA_BLE_DEVICE";
    private final static int REQUEST_ENABLE_BT = 0, SCAN_DEVICE = 1;
    ProgressDialog pDialog;
    private static String SHARED_PREF_KEY = "com.mbientlab.metawear.example.MainActivity", ROUTE_STATE = "route_state", ROUTE_ID = "route_id";
    public static MetaWearBoard mwBoard;
    public static BluetoothDevice mydevice;
    public static TextView textView;
    private Bmi160Accelerometer bmi160AccModule;
    public static final String EXTRA_BT_DEVICE = "com.mbientlab.metawear.app.NavigationActivity.EXTRA_BT_DEVICE";
    int sampleCounter = 0;
    int lastBeatTime = 0;
    int threshold = 256;
    int ibi = 1;
    int trough = 256;
    int peak = 256;
    boolean pulse = false;
    boolean secondBeat = false;
    int rate[];
    boolean firstBeat = true;
    int bpm = 60;
    int amp = 100;
    private TextView text_sleep;
    ProgressDialog progressDialog;
    private java.util.Timer myTimer;
    public static final String ACCEL_DATA="accel_data";
    private Debug debugModule;
    ArrayList<Float> x_arr;
    ArrayList<Float> y_arr;
    ArrayList<Float> z_arr;
    ArrayList<Float> gX_arr;
    ArrayList<Float> gY_arr;
    ArrayList<Float> gZ_arr;
    private Temperature tempModule;
    private Float temp_value ;
    private float accel_x = 0;
    private float accel_y = 0;
    private float accel_z = 0;
    private float gyro_x = 0;
    private float gyro_y = 0;
    private float gyro_z = 0;
    private TextView text_temp;
    long startTime = 0;

    Bmi160Gyro bmi160GyroModule;
    private Settings settingModule;
/*
    TextView textpulse = (TextView) findViewById(R.id.textpulse);*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement);
        getApplicationContext().bindService(new Intent(this, MetaWearBleService.class),
                this, Context.BIND_AUTO_CREATE);
        Log.i("OnCreate", "done with calls");
        Log.i("MacAddress", WhichBaby.macadress + " foo ");
        // action bara geri butonu ekleme
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        text_sleep = (TextView) findViewById(R.id.textSleep);
        text_temp = (TextView) findViewById(R.id.textTemperature);

    }
    @Override
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
        } else if (id == R.id.action_connect) {
            Intent intent = new Intent(this, MeasurementActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_clear_log) {
            mwBoard.disconnect();
        } else if (id == R.id.action_reset_device) {
            resetMe();
        } //geri buttonu
        if(item.getItemId()==android.R.id.home){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        MetaWearBleService.LocalBinder binder = (MetaWearBleService.LocalBinder) service;
        final BluetoothManager btManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothDevice remoteDevice = mydevice;

        binder.executeOnUiThread();
        // Create a MetaWear board object for the Bluetooth Device
        mwBoard = binder.getMetaWearBoard(remoteDevice);

        mwBoard.setConnectionStateHandler(new MetaWearBoard.ConnectionStateHandler() {
            @Override
            public void connected() {
                pDialog.hide();
                // pDialog.hide();
                Toast.makeText(MeasurementActivity.this, "Connected", Toast.LENGTH_LONG).show();

                Log.i("test", "Connected");
                Log.i("test", "MetaBoot? " + mwBoard.inMetaBootMode());


                mwBoard.readDeviceInformation().onComplete(new AsyncOperation.CompletionHandler<MetaWearBoard.DeviceInformation>() {
                    @Override
                    public void success(MetaWearBoard.DeviceInformation result) {
                        Log.i("test", "Device Information: " + result.toString());
                        batteryMe();


                    }

                    @Override
                    public void failure(Throwable error) {
                        Log.e("test", "Error reading device information", error);
                    }
                });

                callAsynchronousTask();

            }

            @Override
            public void disconnected() {
                // pDialog.hide();
                Toast.makeText(MeasurementActivity.this, "Disconnected", Toast.LENGTH_LONG).show();
                Log.i("test", "Disconnected");


            }

        });
        mwBoard.connect();
        pDialog = new ProgressDialog(MeasurementActivity.this);
        pDialog.setMessage(getApplicationContext().getString(R.string.please_wait));
        pDialog.setCancelable(true);
        pDialog.show();


    }



    public void callAsynchronousTask() {
        final Handler handler = new Handler();
        java.util.Timer timer = new java.util.Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            temp_value = 0f;
                            startTime = System.currentTimeMillis();

                            BackgroundTask performBackgroundTask = new BackgroundTask();
                            // PerformBackgroundTask this class is the class that extends AsyncTask
                            Log.i("test", "Task begins");
                            performBackgroundTask.execute();

                            x_arr.clear();
                            y_arr.clear();
                            z_arr.clear();
                            gX_arr.clear();
                            gY_arr.clear();
                            gZ_arr.clear();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
               // handler.removeCallbacksAndMessages(null);
            }
        };
        timer.schedule(doAsynchronousTask, 10 , 1*60*1000); //2 dk çalışsın
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    private void doExit() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                MeasurementActivity.this);

        alertDialog.setPositiveButton("Evet", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //debugModule.resetDevice();
                mwBoard.disconnect();
                finish();
            }
        });

        alertDialog.setNegativeButton("Hayır", null);

        alertDialog.setMessage("Uygulamadan çıkmak istediğinize emin misiniz?");
        alertDialog.setTitle("BabyWristband");
        alertDialog.show();
    }

    public void onBackPressed() {
        doExit();
    }


    public void resetMe() {
        try {
            mwBoard.getModule(Debug.class).resetDevice();
        } catch (UnsupportedModuleException e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public void batteryMe() {
        mwBoard.readBatteryLevel().onComplete(new AsyncOperation.CompletionHandler<Byte>() {
            @Override
            public void success(final Byte result) {
                ((TextView) findViewById(R.id.action_sarj)).setText(String.format(Locale.US, "%d", result));
            }
            @Override
            public void failure(Throwable error) {
                Log.e("test", "Error reading battery level", error);
            }
        });
    }



    /*----------------------------------BackgroundTask------------------------------------------------------------*/
    private class BackgroundTask extends AsyncTask<Void, Void, float[]> {

        private Float tempBack=0f;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MeasurementActivity.this);
            progressDialog.setMessage("Ölçüm yapılıyor lütfen 5 dakika boyunca bekleyiniz ve uygulamayı kapatmayınız...");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        protected float[] doInBackground(Void... arg0) {
         //   startTime = System.currentTimeMillis();
            x_arr = new ArrayList<Float>();
            y_arr = new ArrayList<Float>();
            z_arr = new ArrayList<Float>();
            gX_arr = new  ArrayList<Float>();
            gY_arr = new  ArrayList<Float>();
            gZ_arr = new  ArrayList<Float>();


            try {

                bmi160AccModule = mwBoard.getModule(Bmi160Accelerometer.class);

            /*  bmi160AccModule.configureAxisSampling()
                        .setFullScaleRange(Bmi160Accelerometer.AccRange.AR_2G)
                        .setOutputDataRate(Bmi160Accelerometer.OutputDataRate.ODR_3_125_HZ)
                        .enableUndersampling((byte) 4)
                        .commit();   */

               bmi160AccModule.configureAxisSampling()
                        .setFullScaleRange(Bmi160Accelerometer.AccRange.AR_2G)
                        .setOutputDataRate(Bmi160Accelerometer.OutputDataRate.ODR_50_HZ)
                        .commit();

                bmi160GyroModule= mwBoard.getModule(Bmi160Gyro.class);

                bmi160GyroModule.configure()
                        .setFullScaleRange(Bmi160Gyro.FullScaleRange.FSR_250)
                        .setOutputDataRate(Bmi160Gyro.OutputDataRate.ODR_50_HZ)
                        .commit();



            } catch (UnsupportedModuleException e) {
                e.printStackTrace();
            }

            while (((System.currentTimeMillis() - startTime)/1000) < 30) {

                bmi160AccModule.enableAxisSampling();
                bmi160AccModule.start();
                bmi160GyroModule.start();
              //  bmi160AccModule.startLowPower();

                bmi160AccModule.routeData().fromAxes().stream(ACCEL_DATA).commit()
                        .onComplete(new AsyncOperation.CompletionHandler<RouteManager>() {
                            @Override
                            public void success(RouteManager result) {
                                result.subscribe(ACCEL_DATA, new RouteManager.MessageHandler() {
                                    @Override
                                    public void process(Message message) {
                                        Log.i("test", message.getData(CartesianFloat.class).toString());
                                        //   final CartesianFloat axisData = message.getData(CartesianFloat.class);
                                        final CartesianFloat axisData = message.getData(CartesianFloat.class);
                                        Log.i("test", String.valueOf(axisData.x().floatValue()));
                                        accel_x = axisData.x().floatValue();
                                        accel_y = axisData.y().floatValue();
                                        accel_z = axisData.z().floatValue();
                                        Log.i("test", String.valueOf(accel_x));
                                        Log.i("test", String.valueOf(accel_y));
                                        Log.i("test", String.valueOf(accel_z));
                                        x_arr.add(accel_x);
                                        y_arr.add(accel_y);
                                        z_arr.add(accel_z);
                                    }
                                });

                            }
                        });

                //Stream rotation data around the XYZ axes from the gyro sensor
                bmi160GyroModule.routeData().fromAxes().stream("gyro_stream").commit()
                        .onComplete(new AsyncOperation.CompletionHandler<RouteManager>() {
                            @Override
                            public void success(RouteManager result) {
                                result.subscribe("gyro_stream", new RouteManager.MessageHandler() {
                                    @Override
                                    public void process(Message msg) {
                                        final CartesianFloat spinData = msg.getData(CartesianFloat.class);
                                        gyro_x= spinData.x().floatValue();
                                        gyro_y= spinData.y().floatValue();
                                        gyro_z= spinData.z().floatValue();

                                        gX_arr.add(gyro_x);
                                        gY_arr.add(gyro_y);
                                        gZ_arr.add(gyro_z);

                                        Log.i("test", spinData.toString());
                                        Log.i("test", String.valueOf(gyro_x));
                                    }
                                });


                            }
                        });

                tempBack = getTemp();


            }

            bmi160AccModule.disableAxisSampling();
            bmi160AccModule.stop();
            bmi160GyroModule.stop();

            float stdX = findDeviation(x_arr);
            Log.i("test", String.valueOf(stdX));
            float stdY = findDeviation(y_arr);
            float stdZ = findDeviation(z_arr);

            float stdGx = findDeviation(gX_arr);
            Log.i("test", String.valueOf(stdGx));
            float stdGy = findDeviation(gY_arr);
            float stdGz = findDeviation(gZ_arr);




            float [] arrStd = new float[7];
            arrStd[0] = stdX;
            arrStd[1] = stdY;
            arrStd[2] = stdZ;
            arrStd[3] = stdGx;
            arrStd[4] = stdGy;
            arrStd[5] = stdGz;
            arrStd[6] = tempBack;



            return arrStd;

        }

        protected void onPostExecute(float[] result) {
            super.onPostExecute(result);
            //     bmi160AccModule.disableAxisSampling();
            //    bmi160AccModule.stop();
            if (progressDialog.isShowing())
                progressDialog.dismiss();

            Log.i("test", String.valueOf(result[0]));
            Log.i("test", String.valueOf(result[1]));
            Log.i("test", String.valueOf(result[2]));
            Log.i("test", String.valueOf(result[3]));
            Log.i("test", String.valueOf(result[4]));
            Log.i("test", String.valueOf(result[5]));
            Log.i("test", String.valueOf(result[6]));

            float stdX_value = result[0];
            float stdY_value = result[1];
            float stdZ_value = result[2];
            float stdGx_value = result[3];
            float stdGy_value = result[4];
            float stdGz_value = result[5];
            float temperature_value = result[6];

            if (stdX_value<0.003 && stdY_value<0.003 && stdZ_value<0.003){
                text_sleep.setText("Sleeping");
            }
            else if(stdY_value>0.4){
                text_sleep.setText("Wake");
            }
            else if(stdGx_value>10){
                text_sleep.setText("Standing/Walking");
            }
            else{
                text_sleep.setText("Sitting");
            }

            text_temp.setText(String.valueOf(temperature_value));



        }

        @Override
        protected void onCancelled(float[] result) {
            super.onCancelled(result);
            progressDialog.dismiss();
            Log.i("test", "Task ends");
            cancel(true);
        }
    }


    public static float findDeviation(ArrayList<Float> arr) {
        float sum =0;
        float avg = 0;
        float squareSum = 0;

        for(int i=0; i<arr.size();i++) {
            sum += arr.get(i).floatValue();
        }
        avg=sum/arr.size();


        for (int i = 0; i < arr.size(); i++) {
            squareSum += Math.pow(arr.get(i).floatValue()-avg, 2);
        }

        return (float) Math.sqrt((squareSum)/(arr.size()-1));

    }

    public float getTemp(){
        try {
            tempModule = mwBoard.getModule(Temperature.class);
        } catch (UnsupportedModuleException e) {
            e.printStackTrace();
        }
        tempModule.routeData().fromSensor().stream("tempC")
                .commit().onComplete(new AsyncOperation.CompletionHandler<RouteManager>() {
            @Override
            public void success(RouteManager result) {
                result.subscribe("tempC", new RouteManager.MessageHandler() {
                    @Override
                    public void process(final Message msg) {
                        Log.i("test", String.format("%.3f C", msg.getData(Float.class)));
                        temp_value = msg.getData(Float.class);
                        // ((TextView) findViewById(R.id.txtTemprature)).setText(String.format("%.3f C", msg.getData(Float.class)).substring(0, 4));
                    }
                });

                tempModule.readTemperature();

            }
        });
        return temp_value;
    }
}
