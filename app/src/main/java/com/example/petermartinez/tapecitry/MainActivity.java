package com.example.petermartinez.tapecitry;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public ArrayList<Thread> threadArrayList;
    public EditText newThread;
    public ListView resultsListView;
    public ArrayAdapter<Thread> mThrSearchAdapter;
    private CursorAdapter mCursorAdapter;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeNewThread();
                Thread thread = new Thread(newThread.getText().toString());
                Snackbar.make(view, "we added a thread", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });

        threadArrayList = new ArrayList<Thread>();
        newThread = (EditText) findViewById(R.id.new_thread_maker);
        resultsListView = (ListView) findViewById(R.id.results_list_view);

        ThreadsSQLiteHelper mDbHelper = new ThreadsSQLiteHelper(MainActivity.this);
        db = mDbHelper.getWritableDatabase();






    }

    private long insertIntoDB(Thread thread){
        ContentValues values = new ContentValues();
        values.put(ThreadsSQLiteHelper.COL_USER_ID, thread.getUser());
        values.put(ThreadsSQLiteHelper.COL_THR_TITLE, thread.getTitle());
        values.put(ThreadsSQLiteHelper.COL_KNOT, -1);
        values.put(ThreadsSQLiteHelper.COL_KNOT_COUNT, 1);
        values.put(ThreadsSQLiteHelper.COL_NODE, -1);
        values.put(ThreadsSQLiteHelper.COL_DATE_CRE, thread.getDateCreated());
        values.put(ThreadsSQLiteHelper.COL_DATE_MOD, thread.getDateModified());
        values.put(ThreadsSQLiteHelper.COL_ASSET_TYPES, thread.assetCheat);
        values.put(ThreadsSQLiteHelper.COL_ASSET_COUNT, thread.assetCheatColor);
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

    public void makeNewThread(){
        Thread thread = new Thread(newThread.getText().toString());
        threadArrayList.add(thread);
        //notifydatasetchanged
    }
}
