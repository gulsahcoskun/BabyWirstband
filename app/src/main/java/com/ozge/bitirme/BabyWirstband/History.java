package com.ozge.bitirme.BabyWirstband;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by TUBA on 7.05.2016.
 */
public class History extends AppCompatActivity {
    DatabaseBabyData BabyInformtion;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

            HistoryListele();
            // action bara geri butonu ekleme
          /*  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            BabyInformtion =new DatabaseBabyData(this);

            SQLiteDatabase db = BabyInformtion.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM Baby", null);
            KayitGoster(cursor);*/
        }


 /*   private void KayitGoster(Cursor cursor){
        ListView lv = (ListView) findViewById(R.id.listState);
        ArrayList<HistoryListItem> list=new ArrayList<HistoryListItem>();
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String ad = cursor.getString((cursor.getColumnIndex("ad")));
            String soyad = cursor.getString((cursor.getColumnIndex("soyad")));
           HistoryListItem nesne=new HistoryListItem(valuem,stp,timee);
            list.add(nesne);

        }
      HistoryListAdapter adp=new HistoryListAdapter(this,list);
        lv.setAdapter(adp);

    }*/
    private void HistoryListele(){
        ListView lv = (ListView) findViewById(R.id.listState);
        ArrayList<HistoryListItem> list=new ArrayList<HistoryListItem>();
        int valuem =1;
        SimpleDateFormat format=new SimpleDateFormat("dd-M-yyyy hh:mm");
        Date timee=new Date();
        String stp="SLEEP";
        HistoryListItem nesne=new HistoryListItem(valuem,stp,timee);
            list.add(nesne);

       HistoryListAdapter adp=new HistoryListAdapter(this,list);
        lv.setAdapter(adp);


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


