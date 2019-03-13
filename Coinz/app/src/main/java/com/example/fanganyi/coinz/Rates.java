package com.example.fanganyi.coinz;

public class Rates {

    private  static double SHIL;
    private  static double DOLR;
    private  static double QUID;
    private  static double PENY;


    public  static void setSHIL(double d){
        SHIL=d;
    }

    public  static void setDOLR(double d){
        DOLR=d;
    }

    public  static void setQUID(double d){
        QUID=d;
    }

    public  static void setPENY(double d){
        PENY=d;
    }

    public  static double getSHIL(){
        return SHIL;
    }

    public  static double getDOLR(){
        return DOLR;
    }

    public  static double getQUID(){
        return QUID;
    }

    public  static double getPENY(){
        return PENY;
    }


}
