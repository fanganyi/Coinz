package com.example.fanganyi.coinz;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;

public class User implements java.io.Serializable{
    private static String s_id;
    private static double s_bankvalue=0.0;
    private static HashMap<String, Coin> s_wallet = new HashMap<String, Coin>();
    private static HashMap<String, User> s_friends = new HashMap<String, User>() ;
    private static ArrayList<Coin> s_bankedCoin = new ArrayList<>();

    private String id;
    private double bankvalue=0.0;
    private HashMap<String, Coin> wallet = new HashMap<String, Coin>();
    private HashMap<String, User> friends = new HashMap<String, User>();
    private ArrayList<Coin> bankedCoin = new ArrayList<>();

    public User(@NonNull String id, @NonNull double v, HashMap<String, Coin> wa, HashMap<String, User> fr, ArrayList<Coin> coi) {
        this.id=id;
        bankvalue=v;
        wallet=wa;
        friends=fr;
        bankedCoin=coi;

    }

    public static void setUser(User u) {
        s_id=u.id;
        s_bankvalue=u.bankvalue;
        s_wallet=u.wallet;
        s_friends=u.friends;
        s_bankedCoin=u.bankedCoin;

    }



    public static double getBankvalue(){
        return s_bankvalue;
    }

    //convert the coin into gold and save in bankvalue
    public static void addBankvalue(@NonNull Coin c){
        if(c.getCurrancy().equals("QUID")&&!c.isIsbankin()){
            s_bankvalue=s_bankvalue+c.getValue()*Rates.getQUID();
        }
        if(c.getCurrancy().equals("PENY")&&!c.isIsbankin()){
            s_bankvalue=s_bankvalue+c.getValue()*Rates.getPENY();
        }
        if(c.getCurrancy().equals("DOLR")&&!c.isIsbankin()){
            s_bankvalue=s_bankvalue+c.getValue()*Rates.getDOLR();
        }
        if(c.getCurrancy().equals("SHIL")&&!c.isIsbankin()){
            s_bankvalue=s_bankvalue+c.getValue()*Rates.getSHIL();
        }
        c.bankin();
    }


    public static void addCoin(@NonNull Coin c) {
        if (s_wallet.size() < 100 && c!=null) {
            s_wallet.put(c.getCoinID(),c);
        }
    }

    public static void removeCoin(@NonNull Coin c){s_wallet.remove(c.getCoinID());}

    @NonNull
    public static String getId(){
        return s_id;
    }

    public static HashMap<String, User> getS_friends(){
        return s_friends;
    }

    public static void addfriend(@NonNull User u){
        s_friends.put(u.id, u);
    }

    public static HashMap<String, Coin> getS_wallet(){
        return s_wallet;
    }

    public static void addBankedcoin(@NonNull Coin c){
        s_bankedCoin.add(c);
        c.bankin();
    }

    public static ArrayList<Coin> getS_bankedCoin(){
        return s_bankedCoin;
    }

    public User(@NonNull String i){
        id=i;
    }

    @NonNull
    public String getid(){
        return id;
    }



}
