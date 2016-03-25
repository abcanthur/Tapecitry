package com.example.petermartinez.tapecitry;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity implements OnMapReadyCallback {
    public ArrayList<Asset> assetArrayList;
    public ListView assetListView;
    public AssetAdapter mAssetAdapter;
    public int[] threadIds;
    public LatLng[] points;
    public String[] titles;
    public int position;
    public SQLiteDatabase db;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        int position = getIntent().getIntExtra("targetPosition", 0);
        threadIds = getIntent().getIntArrayExtra("threadIds");
        String[] query = new String[threadIds.length];
        for (int i = 0; i < threadIds.length; i++) {
            query[i] = String.valueOf(threadIds[i]);
        }

        points = new LatLng[threadIds.length];
        titles = new String[threadIds.length];

        ThreadsSQLiteHelper mDbHelper = ThreadsSQLiteHelper.getInstance(ViewActivity.this);
        db = mDbHelper.getWritableDatabase();
        for (int i = 0; i < threadIds.length; i++) {
            String[] query1 = new String[1];
            query1[0] = query[i];
            Cursor cursor = ThreadsSQLiteHelper.getInstance(ViewActivity.this).getThreadsById(query1);
            dumpCursorForPointsTitles(cursor, i);
            cursor.close();
        }

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);



            assetArrayList = new ArrayList<Asset>();

            assetArrayList.add(new Asset(false));
            assetArrayList.add(new Asset(true));
            assetArrayList.add(new Asset(false));
            assetArrayList.add(new Asset(true));

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
        for (int i = 0; i < threadIds.length; i++) {
            if (i == position) {
                mMap.addMarker(new MarkerOptions().position(points[i]).title(titles[i]));
                mapCenter = points[i];
            } else {
                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.pointred)).position(points[i]).title(titles[i]));
            }
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mapCenter));
    }

    public void dumpCursorForPointsTitles(Cursor cursor, int i) {
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            titles[i] = cursor.getString(1);
            double lat = Double.parseDouble(cursor.getString(2));
            double lon = Double.parseDouble(cursor.getString(3));
            points[i] = new LatLng(lat, lon);
            cursor.moveToNext();
        }
    }
}

