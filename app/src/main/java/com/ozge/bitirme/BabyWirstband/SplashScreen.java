package com.ozge.bitirme.BabyWirstband;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ozge on 6.5.2016.
 */
public class SplashScreen extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        splash_Screen();
    }
    // anlık açılan ekran
    private void splash_Screen(){


        // Thread hazırlanıyor
        Thread thread = new Thread() {

            @Override
            public void run() {

                try {
                    synchronized (this) {
                        // Uygulama 4 saniye aynı ekranda bekliyor
                        wait(2000);
                    }
                } catch (InterruptedException e) {

                    // Hata yönetimi

                } finally {

                    finish();
                    // Yeni açılmak istenen Intent
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(),MainActivity.class);
                    startActivity(intent);

                }

            }
        };

        // Thread başlatılıyor
        thread.start();
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

}
