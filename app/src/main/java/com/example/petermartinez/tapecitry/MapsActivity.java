package com.example.petermartinez.tapecitry;


import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    public int[] threadIds;
    public double[] lats;
    public double[] lons;
    public LatLng[] points;
    public String[] titles;
    public int position;
    public int numberOfMarkers;
    public SQLiteDatabase db;
    private TextView mapsTitle;
    private TextView mapsDistance;
    public final float GALat = 37.791066f;
    public final float GALon = -122.401403f;


    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mapsTitle = (TextView) findViewById(R.id.maps_title);
        mapsDistance = (TextView) findViewById(R.id.maps_distance);



//        final int position = getIntent().getIntExtra("targetPosition", 0);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MapsActivity.this);

        position = sharedPreferences.getInt(MainActivity.LIST_POSITION, 0);


        threadIds = getIntent().getIntArrayExtra("threadIds");
        numberOfMarkers = threadIds.length;
        String[] query = new String[numberOfMarkers];
        for (int i = 0; i < threadIds.length; i++) {
            query[i] = String.valueOf(threadIds[i]);
        }

        points = new LatLng[numberOfMarkers];
        lats = new double[numberOfMarkers];
        lons = new double[numberOfMarkers];
        titles = new String[numberOfMarkers];

        ThreadsSQLiteHelper mDbHelper = ThreadsSQLiteHelper.getInstance(MapsActivity.this);
        db = mDbHelper.getWritableDatabase();
        for (int i = 0; i < numberOfMarkers; i++) {
            String[] query1 = new String[1];
            query1[0] = query[i];
            Cursor cursor = ThreadsSQLiteHelper.getInstance(MapsActivity.this).getThreadsById(query1);
            dumpCursorForPointsTitles(cursor, i);
            cursor.close();
        }
        mapsTitle.setText(titles[position]);
        String distanceInMiles = ThreadAdapter.formatDistance(Thread.haversineTwoPoints(GALat, GALon, (float) lats[position], (float) lons[position]));
        mapsDistance.setText(distanceInMiles + " miles");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map_map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng mapCenter = new LatLng(0,0);
        for (int i = 0; i < numberOfMarkers; i++) {
            if (i == position) {
                mMap.addMarker(new MarkerOptions().position(points[i]).title(titles[i]));
                mapCenter = points[i];
            } else {
                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.pointred)).position(points[i]).title(titles[i]));
            }
            LatLng origin = new LatLng(GALat, GALon);
            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(android.R.drawable.ic_delete)).position(origin).title("You are here"));
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCenter,15));
        double lat1 = getDoubleMin(lats);
        double lon1 = getDoubleMin(lons);
        double lat2 = getDoubleMax(lats);
        double lon2 = getDoubleMax(lons);
        if(lat1 == lat2){
            lat1 = lat1 - .0025;
            lat2 = lat2 + .0025;
        }
        if(lon1 == lon2){
            lon1 = lon1 - .0025;
            lon2 = lon2 + .0025;
        }
        LatLngBounds zoomToMapMarkers = new LatLngBounds(new LatLng(lat1, lon1), new LatLng(lat2, lon2));

        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(zoomToMapMarkers, 40));
    }

    public void dumpCursorForPointsTitles(Cursor cursor, int i) {
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            titles[i] = cursor.getString(1);
            double lat = Double.parseDouble(cursor.getString(2));
            double lon = Double.parseDouble(cursor.getString(3));
            lats[i] = lat;
            lons[i] = lon;
            points[i] = new LatLng(lat, lon);
            cursor.moveToNext();
        }
    }

    public double getDoubleMin(double[] array){
        double min = array[0];
        for(int i = 1; i < array.length; i++){
            if(min > array[i]){
                min = array[i];
            }
        }
        return min;
    }

    public double getDoubleMax(double[] array){
        double max = array[0];
        for(int i = 1; i < array.length; i++){
            if(max < array[i]){
                max = array[i];
            }
        }
        return max;
    }
}

