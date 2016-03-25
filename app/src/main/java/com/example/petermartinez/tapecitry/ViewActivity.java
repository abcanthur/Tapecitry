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

public class ViewActivity extends AppCompatActivity implements OnMapReadyCallback {
    public ArrayList<Asset> assetArrayList;
    public ListView assetListView;
    public AssetAdapter mAssetAdapter;
    public int[] threadIds;
    public double[] lats;
    public double[] lons;
    public LatLng[] points;
    public String[] titles;
    public int position;
    public int numberOfMarkers;
    public SQLiteDatabase db;
    private TextView viewTextView;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        viewTextView = (TextView) findViewById(R.id.view_text_view);



//        final int position = getIntent().getIntExtra("targetPosition", 0);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ViewActivity.this);

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

        ThreadsSQLiteHelper mDbHelper = ThreadsSQLiteHelper.getInstance(ViewActivity.this);
        db = mDbHelper.getWritableDatabase();
        for (int i = 0; i < numberOfMarkers; i++) {
            String[] query1 = new String[1];
            query1[0] = query[i];
            Cursor cursor = ThreadsSQLiteHelper.getInstance(ViewActivity.this).getThreadsById(query1);
            dumpCursorForPointsTitles(cursor, i);
            cursor.close();
        }
        viewTextView.setText(titles[position]);

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);



            assetArrayList = new ArrayList<Asset>();

        for(int i = 0; i < 10; i++){
            if(Math.random() > .5){
                if(Math.random() > .5){
                    assetArrayList.add(new Asset(false));
                } else {
                    assetArrayList.add(new Asset(true));
                }
            }
        }

            assetListView = (ListView) findViewById(R.id.assets_list_view);
            mAssetAdapter = new AssetAdapter(this, assetArrayList);
            assetListView.setAdapter(mAssetAdapter);


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
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCenter,15));
        LatLngBounds zoomToMapMarkers = new LatLngBounds(new LatLng(getDoubleMin(lats), getDoubleMin(lons)), new LatLng(getDoubleMax(lats), getDoubleMax(lons)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(zoomToMapMarkers, 16));
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

