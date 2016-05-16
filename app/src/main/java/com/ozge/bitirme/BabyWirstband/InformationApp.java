package com.ozge.bitirme.BabyWirstband;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by TUBA on 7.05.2016.
 */
public class InformationApp extends AppCompatActivity {
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_app);
            // action bara geri butonu ekleme
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    // geri buttonu actionbar
    public boolean onOptionsItemSelected(MenuItem item) {
        //geri buttonu
        if(item.getItemId()==android.R.id.home){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
