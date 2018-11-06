package com.example.fanganyi.coinz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;


import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private final String tag = "MainActivity";
    private String downloadDate = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());

    // Format: YYYY/MM/DD

    private final String preferencesFile = "MyPrefsFile"; // for storing preferences

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //download file from network
        try {
            new DownloadFileTask().execute("http://homepages.inf.ed.ac.uk/stg/coinz/"+downloadDate+"/coinzmap.geojson");

            // thread to sleep for 1000 milliseconds
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Download didn't occur");
        }

        // Set up user interface as usual
        setContentView(R.layout.activity_main);
    }

    public void openMap(View view){
        Intent openmap = new Intent (this, MapActivity.class);
        startActivity(openmap);

    }

    @Override
    public void onStart() {
        super.onStart();

        // Restore preferences
        SharedPreferences settings = getSharedPreferences(preferencesFile, Context.MODE_PRIVATE);
        // use ”” as the default value (this might be the first time the app is run)
        //downloadDate = settings.getString("lastDownloadDate", "");
        Log.d(tag, "[onStart] Recalled lastDownloadDate is ’" + downloadDate + "’");

    }


    @Override
    public void onStop() {
        super.onStop();
        Log.d(tag, "[onStop] Storing lastDownloadDate of " + downloadDate);
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(preferencesFile, Context.MODE_PRIVATE);
        // We need an Editor object to make preference changes.
        SharedPreferences.Editor editor = settings.edit(); editor.putString("lastDownloadDate", downloadDate);
        // Apply the edits!
        editor.apply();
    }
}
