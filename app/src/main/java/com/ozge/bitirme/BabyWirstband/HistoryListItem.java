package com.ozge.bitirme.BabyWirstband;

import java.util.Date;

/**
 * Created by TUBA on 25.04.2016.
 */

public class HistoryListItem {
    private int imgn;
    private String sptvalue;
    private Date timee;

        public HistoryListItem(int imgn, String sptvalue, Date timee){
            super();
            this.imgn =imgn;
            this.sptvalue =sptvalue;
            this.timee =timee;

        }

    public int getImgn(){
            return imgn;
        }
    public void setValue(int imgn) {
        this.imgn =imgn;
    }

    public String getSptvalue(){
            return sptvalue;
        }
    public void setSptvalue(String sptvalue) {
        this.sptvalue =sptvalue;
    }

    public Date gettimee(){
            return timee;
        }
    public void setTimee(Date timee) {
        this.timee =timee;
    }
}
