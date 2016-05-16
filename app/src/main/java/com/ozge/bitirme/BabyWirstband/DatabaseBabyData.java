package com.ozge.bitirme.BabyWirstband;

/**
 * Created by TUBA on 24.04.2016.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseBabyData extends SQLiteOpenHelper {

    private static final String VERITABANI = "BABYINFORMATION";
    private static final int SURUM =1;

    public DatabaseBabyData(Context cont){
        super (cont,VERITABANI,null,SURUM);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("CREATE TABLE BabyInformation(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,surname TEXT,birthday TEXT,gender INTEGER );");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXIST  BabyInformation");
        onCreate(db);


    }

}