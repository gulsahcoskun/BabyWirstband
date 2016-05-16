package com.ozge.bitirme.BabyWirstband;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Created by TUBA on 7.05.2016.
 */
public class InformationBaby extends AppCompatActivity {
   DatabaseBabyData  babyInformation;
    EditText EditName;
    EditText EditSurname;
    EditText EditDate;
    CheckBox chckBoxBoy;
    CheckBox chckBoxGirl;
    RadioGroup RdgrpGender;
    RadioButton rbSecilen;
    TextView AddState;
    String ad;
    String soyad;
    String birthDay;
    int gender;
    ImageButton btnAdd2;
    private String[] sutunlar={"name","surname"};
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informationbaby);
            // action bara geri butonu ekleme
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            btnAdd2=(ImageButton)findViewById(R.id.imageAdd);
            btnAdd2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddBabyInformation();
                }
            });

        }
    public boolean onOptionsItemSelected(MenuItem item) {
        //geri buttonu
        if(item.getItemId()==android.R.id.home){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void AddNowSee(){
        AddState=(TextView)findViewById(R.id.txtAddState);
        SQLiteDatabase db=babyInformation.getReadableDatabase();
        Cursor oku=db.query(" BabyInformation", sutunlar, null, null, null, null, null);
            while(oku.moveToNext()){
            String add=oku.getString(oku.getColumnIndex("name"));
            String soyadd=oku.getString(oku.getColumnIndex("surname"));
            AddState.setText("Bebek"+"  "+add + "  "+ soyadd + "  " + "eklendi");
        }

    }
    public void AddBabyInformation(){
        babyInformation=new DatabaseBabyData(this);
        EditName=(EditText)findViewById(R.id.editTxtName);
        EditSurname=(EditText)findViewById(R.id.editTxtSurame);
        EditDate =(EditText)findViewById(R.id.editTxtDate);
        ad=EditName.getText().toString();
        soyad=EditSurname.getText().toString();
        birthDay=EditDate.getText().toString();
        RdgrpGender=(RadioGroup)findViewById(R.id.radioGroup1);
        int selectedId =  RdgrpGender.getCheckedRadioButtonId();
        rbSecilen = (RadioButton) findViewById(selectedId);

        if(rbSecilen.getText().toString().equals("Girl")){
            gender=1;
        }
        else{
            gender=0;

        }
        try {
            SQLiteDatabase db=babyInformation.getWritableDatabase();
            ContentValues cvBaby=new ContentValues();
            cvBaby.put("name",ad);
            cvBaby.put("surname",soyad);
            cvBaby.put("birthday", birthDay);
            cvBaby.put("gender",gender);
            db.insertOrThrow("BabyInformation",null,cvBaby);
            AddNowSee();
        }   finally{
            babyInformation.close();
        }
    }
}
