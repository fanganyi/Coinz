package com.example.fanganyi.coinz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.gson.JsonObject;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {

    private final String tag = "MainActivity";
    private String downloadDate = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
    // Format: YYYY/MM/DD
    private final String preferencesFile = "MyPrefsFile"; // for storing preferences
    private static HashMap<String, Coin> coins= new HashMap<String,Coin>(50);
    private File file = new File("id.txt");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //download Json file from network
        try {
            // download Json file, get the file as a String
            String result = new DownloadFileTask().execute("http://homepages.inf.ed.ac.uk/stg/coinz/"+downloadDate+"/coinzmap.geojson").get();
            //extract all the information in the file and create relative object in further use
            extractInfo(result);

        } catch (Exception e) {
            System.out.println("Download didn't occur");
        }

        //initialize user information at here
        initialUser();

        /*
          manually add some friends for testing
        User jack= new User("jack",8.0,null,null,null);
        User marry= new User("marry",8.0,null,null,null);
        User.addfriend(jack);
        User.addfriend(marry);
         */


        // Set up user interface as usual
        setContentView(R.layout.activity_main);
    }

    //open map button
    public void openMap(View view) {
        Intent openmap = new Intent(this, MapActivity.class);
        startActivity(openmap);
    }

    //show friends list button
    public void openFriends(View view){
        Intent open = new Intent(this, FriendsActivity.class);
        startActivity(open);
    }

    //show coins in wallet button
    public void openWallet(View view){
        Intent open = new Intent(this, WalletCoins.class);
        startActivity(open);
    }

    //show information about bank button
    public void showBank(View view){
        Intent open = new Intent(this, BankActivity.class);
        startActivity(open);
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences settings = getSharedPreferences(preferencesFile, Context.MODE_PRIVATE);

        // Restore preferences

        // use ”” as the default value (this might be the first time the app is run)
        //downloadDate = settings.getString("lastDownloadDate", "");
        Log.d(tag, "[onStart] Recalled lastDownloadDate is ’" + downloadDate + "’");
    }


    @Override
    public void onStop() {
        super.onStop();

        //save user's current information, so next time open this game will continue from last time.
        try{
            PrintWriter writer = new PrintWriter(file);
            FileOutputStream out = new FileOutputStream(file);
            ObjectOutputStream o = new ObjectOutputStream(out);
            User me = new User(User.getId(),User.getBankvalue(), User.getS_wallet(), User.getS_friends(),User.getS_bankedCoin());
            o.writeObject(me);
            o.close();
            out.close();
            Log.d(tag, "[onStop] save user in id.txt");

        }catch(FileNotFoundException e){
            System.out.print("cannot find file");
        }catch (IOException e){
            System.out.print("io exception");
        }

        Log.d(tag, "[onStop] Storing lastDownloadDate of " + downloadDate);
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(preferencesFile, Context.MODE_PRIVATE);
        // We need an Editor object to make preference changes.
        SharedPreferences.Editor editor = settings.edit(); editor.putString("lastDownloadDate", downloadDate);
        // Apply the edits!
        editor.apply();

    }

    public static void addCoin(Coin c){
        if(c!=null){coins.put(c.getCoinID(),c);}
}

    @NonNull
    public static HashMap<String,Coin> getCoins(){
        return coins;
    }

    @NonNull
    public static Coin getCoin(@NonNull String id){
        return coins.get(id);
    }

    public void extractInfo(@NonNull String result) throws JSONException {
        //get all the currency rates

        JSONObject o = new JSONObject(result);
        JSONObject rate = o.getJSONObject("rates");
        double shil = rate.getDouble("SHIL");
        double dolr = rate.getDouble("DOLR");
        double quid = rate.getDouble("QUID");
        double peny = rate.getDouble("PENY");

        //store rates in Rates static class
        Rates.setSHIL(shil);
        Rates.setDOLR(dolr);
        Rates.setPENY(peny);
        Rates.setQUID(quid);

        //get all information about coins
        //get feature collection in result
        FeatureCollection fc = FeatureCollection.fromJson(result);
        //get a list of feature
        List<Feature> fs = fc.features();
        //for each feature
        for(int i=0; i<fs.size();i++) {

            Feature f = fs.get(i);
            Point p = Point.fromJson(f.geometry().toJson());
            List<Double> l= p.coordinates();

            JsonObject j = f.properties();
            String id =  j.get("id").getAsString();
            double value =  j.get("value").getAsDouble();
            String currency =  j.get("currency").getAsString();
            String symbol =  j.get("marker-symbol").getAsString();

            //construct coin and add to coins
            Coin coin = new Coin( l.get(1), l.get(0), id, value,currency,symbol);
            addCoin(coin);

        }
        Log.d(tag, "rates and coins are extracted");
    }

    public void initialUser(){
        //if id file exist, we read the file and set up the data
        if(file.exists() && file !=null){
            try{
                FileInputStream fi = new FileInputStream(file);
                ObjectInputStream oi = new ObjectInputStream(fi);
                User me = (User) oi.readObject();
                User.setUser(me);
                oi.close();
                fi.close();

                //coins in wallet need to set collect boolean on coins hashmap
                for(String coinID: User.getS_wallet().keySet()){
                    if(coins.containsKey(coinID)){
                        coins.get(coinID).collected();
                    }
                }
                //coins in banked list should be set bankedin
                for(Coin c: MainActivity.getCoins().values()){
                    if(User.getS_bankedCoin().contains(c)){
                        c.bankin();
                    }
                }
            }catch(IOException e){
                System.out.println("Error initializing stream");

            }catch (ClassNotFoundException e){
                System.out.println("class not found");
            }
            Log.d(tag,"load user info");
        }
        // if this is the first time run the app, initial a new user
        else{
            User a= new User(UUID.randomUUID().toString());
            User.setUser(a);
            Log.d(tag,"create new user");
        }
    }


}
