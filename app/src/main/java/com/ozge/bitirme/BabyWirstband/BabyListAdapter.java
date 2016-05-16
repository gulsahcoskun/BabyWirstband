package com.ozge.bitirme.BabyWirstband;

/**
 * Created by TUBA on 25.04.2016.
 */
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class BabyListAdapter extends BaseAdapter{
LayoutInflater layoutInflater;
    List<BabyItem> list;
    public BabyListAdapter(Activity activity, List<BabyItem> listle){
       layoutInflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       list=listle;

    }
    @Override
    public int getCount(){
    return list.size();
    }
    @Override
    public Object getItem(int position){
     return list.get(position);
    }
    public long getItemId(int position){

        return position;
    }
    public View getView(int position,View convertView,ViewGroup parent){

  View satir;
     satir=layoutInflater.inflate(R.layout.activity_babyitem,null);
     ImageView img=(ImageView)satir.findViewById(R.id.imgBttnGenderItem);
     TextView tctAd=(TextView)satir.findViewById(R.id.txtNameItems);
     TextView tctSoyad=(TextView)satir.findViewById(R.id.txtSurnameItems);
     TextView tctDate=(TextView)satir.findViewById(R.id.txtDateItems);
     BabyItem child=list.get(position);

     if (String.valueOf(child.getImgn())!="1") {
         img.setImageResource(R.mipmap.ic_babybtn);
     }
     else{
         img.setImageResource(R.mipmap.ic_emzik);
     }
     tctAd.setText(String.valueOf(child.getAdi()));
     tctSoyad.setText(String.valueOf(child.getSoyad()));
     tctDate.setText(String.valueOf(child.getBirthday()));
     return satir;


 }


}