package com.example.fanganyi.coinz;


import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.mapbox.android.core.location.LocationEngineListener;
import com.mapbox.android.core.location.LocationEnginePriority;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, LocationEngineListener, PermissionsListener {
    private String tag="MapActivity";
    private MapView mapView;
    private MapboxMap map;
    private PermissionsManager permissionsManager;
    private Location originLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1IjoiZmFuZ2FuIiwiYSI6ImNqbnZ1MGdhMjAybnMza3F4YmZzYzlsNmQifQ.RdjXuw-917JCRpJVEhBbNg");
        setContentView(R.layout.activity_map);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        Log.d(tag, "onCreate");

    }

    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        if(mapboxMap==null){
            Log.d(tag,"[onMapReady] mapBox is null");
        }else{
            map = mapboxMap;
            map.getUiSettings().setCompassEnabled(true);
            map.getUiSettings().setZoomControlsEnabled(true);
            enableLocation();

        }

        //get coins saved in main activities and loop each coin to add marker on map based on their currency.
        // when click the coin value of the coin will shown.
        assert mapboxMap != null;
        //for each coin in MainActivity
        for (@NonNull Coin c : MainActivity.getCoins().values()) {
            //check if this coin is collected before
            if (c.isCollected()) {
                //if this coin is collected before, add grey marker on map, and add some basic information of this coin
                //when click this coin on map, basic information would shown on screen.
                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(c.getLat(), c.getLng()))
                        .snippet(c.getSymbol() + " " + c.getCurrancy())
                        .title(c.getCoinID())
                        .icon(IconFactory.getInstance(MapActivity.this).fromResource(R.drawable.cannot_collect)));
            } else {
                //if this coin has not been collected before, add marker with different color based on its currency
                //if th coin currency is DOLR, adding green marker
                if (c.getCurrancy().equals("DOLR")) {
                    //show marker on map
                    mapboxMap.addMarker(new MarkerOptions()
                            .position(new LatLng(c.getLat(), c.getLng()))
                            .snippet(c.getSymbol() + " " + c.getCurrancy()+" picked")
                            .title(c.getCoinID())
                            .icon(IconFactory.getInstance(MapActivity.this).fromResource(R.drawable.pdolr)));

                }
                //if the coin currency is QUID, adding yellow marker.
                if (c.getCurrancy().equals("QUID")) {

                    mapboxMap.addMarker(new MarkerOptions()
                            .position(new LatLng(c.getLat(), c.getLng()))
                            .snippet(c.getSymbol() + " " + c.getCurrancy())
                            .title(c.getCoinID())
                            .icon(IconFactory.getInstance(MapActivity.this).fromResource(R.drawable.pquid)));
                }
                //if the coin currency is SHIL, adding blue marker
                if (c.getCurrancy().equals("SHIL")) {
                    mapboxMap.addMarker(new MarkerOptions()
                            .position(new LatLng(c.getLat(), c.getLng()))
                            .snippet(c.getSymbol() + " " + c.getCurrancy())
                            .title(c.getCoinID())
                            .icon(IconFactory.getInstance(MapActivity.this).fromResource(R.drawable.pshil)));

                }
                //if the coin currency is PENY, adding red marker
                if (c.getCurrancy().equals("PENY")) {
                    mapboxMap.addMarker(new MarkerOptions()
                            .position(new LatLng(c.getLat(), c.getLng()))
                            .snippet(c.getSymbol() + " " + c.getCurrancy())
                            .title(c.getCoinID())
                            .icon(IconFactory.getInstance(MapActivity.this).fromResource(R.drawable.ppeny)));

                }
            }
        }

        //a log message in log showing 50 coins have been added on map
        Log.d(tag,"50 coins have added");

    }

    @SuppressWarnings( {"MissingPermission"})
    private void enableLocation() {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            Log.d(tag,"Permissions are granted");
            // Activate the MapboxMap LocationComponent to show user location
            // Adding in LocationComponentOptions is also an optional parameter
            LocationComponent locationComponent = map.getLocationComponent();
            locationComponent.activateLocationComponent(this);
            locationComponent.setLocationComponentEnabled(true);
            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);
            originLocation = locationComponent.getLastKnownLocation();
            pickingCoin(originLocation);
//            System.out.println(originLocation.toString());
            Log.d(tag, "update location");
            onConnected();
            locationComponent.getLocationEngine().setPriority(LocationEnginePriority.HIGH_ACCURACY);
            onLocationChanged(map.getLocationComponent().getLastKnownLocation());

            //Log.d(tag, locationComponent.getLocationEngine().getLastLocation().toString());
            //pickingCoin(locationComponent.getLocationEngine().getLastLocation());
        } else {
            Log.d(tag,"Permissions are not granted");
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    private void setCameraPosition(Location location) {
        map.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
    }

    @Override
    public void onLocationChanged(Location location) {
//        System.out.println("changed location"+location.toString());
        if (location == null) {
            Log.d(tag, "[onLocationChanged] location is null");
        } else {
            Log.d(tag, "[onLocationChanged] location is not null");
            originLocation = location;
            setCameraPosition(location);
        } }


    @Override @SuppressWarnings("MissingPermission") public void onConnected() {
        Log.d(tag, "[onConnected] requesting location updates");
        map.getLocationComponent().getLocationEngine().requestLocationUpdates();
    }
    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
        Log.d(tag,"Permissions: "+permissionsToExplain.toString());
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            enableLocation();
            Log.d(tag, "[Location] :"+originLocation.toString());
        }else{
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    @SuppressWarnings("MissingPermission")
    protected void onStart() {
        super.onStart();
        mapView.onStart();
        Log.d(tag,"onstart");
    }

    public void pickingCoin(Location l){
        //a coinlist contains all the coin near user
        //a coin is near user if the coin is within acceptableDifference from user
        //construct this list first
        if(User.getS_wallet().size()<100) {
            ArrayList<Coin> maypick = new ArrayList<>();
            Log.d(tag, "[pickingcoin] start picking coins"+acceptableDifference());
            for (Coin m : MainActivity.getCoins().values()) {
                //find the coins that are not away from user more than 25m->degree in lat
                if (Math.abs(l.getLatitude() - m.getLat()) <= acceptableDifference()) {
                    maypick.add(m);
                }
                //find coins that are not away from user more than 25m->degree in lng
                if (Math.abs(l.getLongitude() -m.getLng()) <= acceptableDifference()) {
                    //if the coin is also within the range in lat and have't collected
                    //we calculate the distance between user and the coin
                    if (!m.isCollected()) {
                        double dist = distFrom(m.getLat(), m.getLng(), l.getLatitude(), l.getLongitude());
                        //if the distance is desired value we collect this coin
                        if (dist <= 25) {
                            Log.d(tag, "[pickingcoin] dist<25");
                            //set this coin has been picked
                            m.collected();
                            //grey this icon on map
                            map.addMarker(new MarkerOptions()
                                    .position(new LatLng(m.getLat(), m.getLng()))
                                    .snippet(m.getSymbol() + " " + m.getCurrancy() + " picked")
                                    .title(m.getCoinID())
                                    .icon(IconFactory.getInstance(MapActivity.this).fromResource(R.drawable.cannot_collect)));
                            //add the coin to user wallet
                            User.addCoin(m);
                            //pop up a message
                            Toast.makeText(this, "you just picked up a coin.", Toast.LENGTH_LONG).show();

                        }

                    }
                }
            }
            Log.d(tag, "[pickingcoin] one round marker done");
        }else {
            Toast.makeText(this, "wallet is full", Toast.LENGTH_LONG).show();
        }

    }
    //convert 25 meters to degree, based on a circle with 6363497m radius
    //I find on line that the earth radius is 6363497 based on edinburgh coordinate
    public double acceptableDifference(){
        double earthRadius = 6363497;
        return 25*180/earthRadius/Math.PI;
    }

    // a function used to calculate distance in meters between two latlng
    public double distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6363497; //
        double dLat = (lat2-lat1)*earthRadius*Math.PI/180;
        double dLng = (lng2-lng1)*earthRadius*Math.PI/180;
        double diff = Math.sqrt(dLat*dLat+dLng*dLng);
        return (diff);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    @SuppressWarnings("MissingPermission")
    protected void onStop() {
        super.onStop();
        LocationComponent locationComponent = map.getLocationComponent();
        locationComponent.setLocationComponentEnabled(false);
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {

        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


}
