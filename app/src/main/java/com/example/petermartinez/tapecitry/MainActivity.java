package com.example.petermartinez.tapecitry;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public ArrayList<Thread> threadArrayList;
    public EditText newThread;
    public ListView resultsListView;
    public ThreadAdapter mThreadAdapter;
    private CursorAdapter mCursorAdapter;
    private SQLiteDatabase db;
    public final float GALat = 37.791066f;
    public final float GALon = -122.401403f;
    public static final String CREATION_TIME_STAMP = "creationTimeStamp";
    public boolean isFirstRun = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(isFirstRun) {
            Intent intent = new Intent(MainActivity.this, DBpreload.class);//preload database if necessary, splash screen
            intent.putExtra(CREATION_TIME_STAMP, System.currentTimeMillis());
            isFirstRun = false;
            startActivity(intent);
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeNewThread();
                Snackbar.make(view, "we added a thread", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });

        threadArrayList = new ArrayList<Thread>();
        newThread = (EditText) findViewById(R.id.new_thread_maker);
        resultsListView = (ListView) findViewById(R.id.results_list_view);
        mThreadAdapter = new ThreadAdapter(this, threadArrayList);



        ThreadsSQLiteHelper mDbHelper = new ThreadsSQLiteHelper(MainActivity.this);
        db = mDbHelper.getWritableDatabase();

        threadArrayList.clear();
        dumpCursorToArray();
        resultsListView.setAdapter(mThreadAdapter);

    }

    private long insertIntoDbFromThread(Thread thread){
        ContentValues values = new ContentValues();
        values.put(ThreadsSQLiteHelper.COL_USER_ID, thread.getUser());
        values.put(ThreadsSQLiteHelper.COL_THR_TITLE, thread.getTitle());
        values.put(ThreadsSQLiteHelper.COL_KNOT, -1);
        values.put(ThreadsSQLiteHelper.COL_KNOT_COUNT, 1);
        values.put(ThreadsSQLiteHelper.COL_NODE, -1);
        values.put(ThreadsSQLiteHelper.COL_DATE_CRE, thread.getDateCreated());
        values.put(ThreadsSQLiteHelper.COL_DATE_MOD, thread.getDateModified());
        values.put(ThreadsSQLiteHelper.COL_ASSET_TYPES, thread.getAssetType());
        values.put(ThreadsSQLiteHelper.COL_ASSET_COUNT, thread.getAssetCount());
        values.put(ThreadsSQLiteHelper.COL_LAT, thread.getLat());
        values.put(ThreadsSQLiteHelper.COL_LON, thread.getLon());
        values.put(ThreadsSQLiteHelper.COL_DUR, thread.getDuration());
        values.put(ThreadsSQLiteHelper.COL_RATING, thread.getRating());
        int ratings = (int) (thread.getViews() / 2.5);
        values.put(ThreadsSQLiteHelper.COL_RATINGS, ratings);
        values.put(ThreadsSQLiteHelper.COL_LENGTH, -1);
        values.put(ThreadsSQLiteHelper.COL_VIEWS, thread.getViews());

        long newRowId = db.insert(ThreadsSQLiteHelper.THREADS_TABLE_NAME, null,values);
        return newRowId;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void makeNewThread() {
        Thread thread = Thread.newRandomThread(newThread.getText().toString());
//        threadArrayList.add(thread);

        insertIntoDbFromThread(thread);
        //notifydatasetchanged

        threadArrayList.clear();
        dumpCursorToArray();
        mThreadAdapter.notifyDataSetChanged();

    }

        public void dumpCursorToArray(){
        ArrayList<Thread> tempThreadArrayList;
        Cursor cursor = db.query("THREADS", null, null, null, null, null, null);
            Toast.makeText(MainActivity.this, String.valueOf(cursor.getCount()), Toast.LENGTH_LONG).show();
        Log.i("DB", "results count: " + String.valueOf(cursor.getCount()));
        int colCount = cursor.getColumnCount();
        Log.i("DB", "columns count: " + String.valueOf(colCount));
        String[] colNames = cursor.getColumnNames();


        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            Thread thread = new Thread("cursor rip");
            Log.d("db output", cursor.getString(0));
//            for(int i = 0; i < colCount; i++){
//                Log.i("DB", colNames[i] + " : " + cursor.getString(i));
//            }
            Log.i("DB", "asset cheat color" + " : " + cursor.getString(cursor.getColumnIndex(ThreadsSQLiteHelper.COL_ASSET_COUNT)));

            thread.setTitle(cursor.getString(cursor.getColumnIndex(ThreadsSQLiteHelper.COL_THR_TITLE)));
            thread.setRating(Float.parseFloat(cursor.getString(cursor.getColumnIndex(ThreadsSQLiteHelper.COL_RATING))));
            thread.setDuration(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ThreadsSQLiteHelper.COL_DUR))));
            thread.setDateCreated((cursor.getLong(cursor.getColumnIndex(ThreadsSQLiteHelper.COL_DATE_CRE))));
            thread.setDateModified((cursor.getLong(cursor.getColumnIndex(ThreadsSQLiteHelper.COL_DATE_MOD))));
            thread.setViews(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ThreadsSQLiteHelper.COL_VIEWS))));
            thread.setLat(Float.parseFloat(cursor.getString(cursor.getColumnIndex(ThreadsSQLiteHelper.COL_LAT))));
            thread.setLon(Float.parseFloat(cursor.getString(cursor.getColumnIndex(ThreadsSQLiteHelper.COL_LON))));
            thread.setAssetType(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ThreadsSQLiteHelper.COL_ASSET_TYPES))));
            thread.setAssetCount(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ThreadsSQLiteHelper.COL_ASSET_COUNT))));
            thread.setUser(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ThreadsSQLiteHelper.COL_USER_ID))));
            thread.setDistToPoint(Thread.haversineTwoPoints(GALat, GALon, thread.getLat(), thread.getLon()));
            thread.setBearingToPoint(Thread.bearing(GALat, GALon, thread.getLat(), thread.getLon()));
            cursor.moveToNext();
            threadArrayList.add(thread);
        }
            cursor.close();

    }

}
