package com.example.fanganyi.coinz;

import android.support.annotation.NonNull;

public class Coin {
     private double lat;
     private double lng;
     private String id;
     private double value;
     private String currency;
     private boolean isCollect = false;
     private String symbol;
     private boolean isbankin=false;



    public Coin(double la,double ln, @NonNull String id, double v, @NonNull String c, @NonNull String symbol){
        lat=la;
        lng=ln;
        this.id=id;
        value=v;
        currency=c;
        this.symbol=symbol;
    }

    public double getValue(){

        return value;
    }

    @NonNull
    public String getCurrancy(){
        return currency;
    }

    public double getLat(){
        return lat;
    }

    public double getLng(){
        return lng;
    }

    @NonNull
    public String getCoinID(){
        return id;
    }

    @NonNull
    public String getSymbol(){
        return symbol;
    }

    public boolean isCollected(){
        return isCollect;
    }

    public void collected(){
        isCollect=true;
    }

    public boolean isIsbankin(){
        return isbankin;
    }

    public void bankin(){
        isbankin=true;
    }

}
