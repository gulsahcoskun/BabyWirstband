package com.ozge.bitirme.BabyWirstband;

/**
 * Created by TUBA on 25.04.2016.
 */

public class BabyItem {
    private int imgn;
    private String ad;
    private String soyad;
    private String birthday;

        public BabyItem(int imgn, String ad, String soyad,String birthday){
            super();
            this.imgn =imgn;
            this.ad =ad;
            this.soyad =soyad;
            this.birthday =birthday;
        }

    public int getImgn(){
            return imgn;
        }
    public void setId(int imgn) {
        this.imgn =imgn;
    }

    public String getAdi(){
            return ad;
        }
    public void setAd(String ad) {
        this.ad =ad;
    }

    public String getSoyad(){
            return soyad;
        }
    public void setSoyad(String soyad) {
        this.soyad =soyad;}

    public String getBirthday(){
        return birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday =birthday;}

}
