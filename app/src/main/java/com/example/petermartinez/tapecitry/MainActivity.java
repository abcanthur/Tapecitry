package com.example.petermartinez.tapecitry;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.ls.LSInput;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    public ArrayList<Thread> threadArrayList;
    public ListView resultsListView;
    public ThreadAdapter mThreadAdapter;
    private SQLiteDatabase db;
    public final float GALat = 37.791066f;
    public final float GALon = -122.401403f;
    public static final String CREATION_TIME_STAMP = "creationTimeStamp";
    public boolean isFirstRun = true;
    public LinearLayout distanceBearing;
    public static final String LIST_POSITION = "listPosition";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handleIntent(getIntent());

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
//                Snackbar.make(view, "we added a thread", Snackbar.LENGTH_SHORT)
//                        .setAction("Action", null).show();
            }
        });


        threadArrayList = new ArrayList<Thread>();
        resultsListView = (ListView) findViewById(R.id.results_list_view);
        mThreadAdapter = new ThreadAdapter(this, threadArrayList);

        resultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                TextView distanceTextView = (TextView) view.findViewById(R.id.distance);
//                TextView directionTextView = (TextView) view.findViewById(R.id.compass);
//
//                distanceTextView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        goToViewActivity(position);
//                    }
//                });
//
//                directionTextView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        goToViewActivity(position);
//                    }
//                });
                goToMapsActivity(position);
            }
        });

        resultsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                goToViewActivity(position);
                return true;
            }
        });






        ThreadsSQLiteHelper mDbHelper = ThreadsSQLiteHelper.getInstance(MainActivity.this);
        db = mDbHelper.getWritableDatabase();

        resultsListView.setAdapter(mThreadAdapter);
        resultsListView.invalidate();

    }

    private void goToViewActivity(int position){
        Intent intent = new Intent(MainActivity.this,ViewActivity.class);
        intent.putExtra("targetPosition", position);
        int[] threadIds = new int[(threadArrayList.size())];
        for(int i = 0; i < threadArrayList.size(); i++ ){
            threadIds[i] = threadArrayList.get(i).getId();
        }
        intent.putExtra("threadIds", threadIds);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(MainActivity.LIST_POSITION, position);
        editor.commit();

        startActivity(intent);
    }
    private void goToMapsActivity(int position){
        Intent intent = new Intent(MainActivity.this,MapsActivity.class);
        intent.putExtra("targetPosition", position);
        int[] threadIds = new int[(threadArrayList.size())];
        for(int i = 0; i < threadArrayList.size(); i++ ){
            threadIds[i] = threadArrayList.get(i).getId();
        }
        intent.putExtra("threadIds", threadIds);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(MainActivity.LIST_POSITION, position);
        editor.commit();

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Cursor cursor = ThreadsSQLiteHelper.getInstance(MainActivity.this).searchThreads(query);
            dumpCursorToArray(cursor);
            cursor.close();
        }
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
        values.put(ThreadsSQLiteHelper.COL_RATINGS, 1);
        values.put(ThreadsSQLiteHelper.COL_LENGTH, -1);
        values.put(ThreadsSQLiteHelper.COL_VIEWS, thread.getViews());

        long newRowId = db.insert(ThreadsSQLiteHelper.THREADS_TABLE_NAME, null,values);
        return newRowId;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            //noinspection SimplifiableIfStatement
            case R.id.action_settings:
                sortResultsBy("Age");
                mThreadAdapter.notifyDataSetChanged();
                resultsListView.invalidate();
                return true;
            case R.id.sortAge:
                sortResultsBy("Age");
                mThreadAdapter.notifyDataSetChanged();
                resultsListView.invalidate();
                return true;
            case R.id.sortDistance:
                sortResultsBy("Distance");
                mThreadAdapter.notifyDataSetChanged();
                resultsListView.invalidate();
                return true;
            case R.id.sortDuration:
                sortResultsBy("Duration");
                mThreadAdapter.notifyDataSetChanged();
                resultsListView.invalidate();
                return true;
            case R.id.sortRating:
                sortResultsBy("Rating");
                mThreadAdapter.notifyDataSetChanged();
                resultsListView.invalidate();
                return true;
            case R.id.sortViews:
                sortResultsBy("Views");
                mThreadAdapter.notifyDataSetChanged();
                resultsListView.invalidate();
                return true;
            case R.id.sortTitle:
                sortResultsBy("Title");
                mThreadAdapter.notifyDataSetChanged();
                resultsListView.invalidate();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void sortResultsBy(String string){
        Toast.makeText(MainActivity.this, "Sort the results by " + string, Toast.LENGTH_SHORT).show();
        Log.i("SORT", string);
        switch (string) {
            case "Age":
                Collections.sort(threadArrayList, Thread.ageComparator);
                break;
            case "Distance":
                Collections.sort(threadArrayList, Thread.distanceComparator);
                break;
            case "Duration":
                Collections.sort(threadArrayList, Thread.durationComparator);
                break;
            case "Rating":
                Collections.sort(threadArrayList, Thread.ratingComparator);
                break;
            case "Views":
                Collections.sort(threadArrayList, Thread.viewsComparator);
                break;
            case "Title":
                Collections.sort(threadArrayList, Thread.titleComparator);
                break;
            default:
                Collections.sort(threadArrayList, Thread.titleComparator);
                break;
        }
    }

    public void makeNewThread() {
        Thread thread = Thread.newRandomThread("");
//        threadArrayList.add(thread);

        insertIntoDbFromThread(thread);
        //notifydatasetchanged

        threadArrayList.clear();
        Cursor cursor = db.query("THREADS", null, null, null, null, null, null);
//        Toast.makeText(MainActivity.this, String.valueOf(cursor.getCount()), Toast.LENGTH_LONG).show();
        Log.i("DB", "results count: " + String.valueOf(cursor.getCount()));
        dumpCursorToArray(cursor);
        cursor.close();
        mThreadAdapter.notifyDataSetChanged();

    }

        public void dumpCursorToArray(Cursor cursor){
            threadArrayList.clear();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            Thread thread = new Thread("cursor rip");
            Log.d("db output", cursor.getString(0));
//            for(int i = 0; i < colCount; i++){
//                Log.i("DB", colNames[i] + " : " + cursor.getString(i));
//            }
            Log.i("DB", "asset cheat color" + " : " + cursor.getString(cursor.getColumnIndex(ThreadsSQLiteHelper.COL_ASSET_COUNT)));

            thread.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ThreadsSQLiteHelper.COL_ID))));
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
            mThreadAdapter.notifyDataSetChanged();
        }


    }

}
