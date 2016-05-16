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

public class HistoryListAdapter extends BaseAdapter{
    LayoutInflater layoutInflater;
    List<HistoryListItem> list;
    public HistoryListAdapter(Activity activity, List<HistoryListItem> listle){
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
        satir=layoutInflater.inflate(R.layout.activity_listhistory_item,null);
        ImageView img=(ImageView)satir.findViewById(R.id.imgBttnSleepingItem);
        TextView stp=(TextView)satir.findViewById(R.id.txtStateItem);
        TextView timee=(TextView)satir.findViewById(R.id.txtDateItem);
        HistoryListItem child=list.get(position);

        if (String.valueOf(child.getImgn())!=null) {
            img.setImageResource(R.mipmap.ic_babybtn);
        }
        stp.setText(String.valueOf(child.getSptvalue()));
        timee.setText(String.valueOf(child.gettimee()));
        return satir;


    }

}