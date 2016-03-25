package com.example.petermartinez.tapecitry;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

/**
 * Created by petermartinez on 3/23/16.
 */
public class DBpreload extends AppCompatActivity {
    public static final String DB_EXISTS = "db_exists";
    private SQLiteDatabase db;
    private static final String TAG = "DB_PRELOAD";
    static long  creationTimeStamp;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            ThreadsSQLiteHelper mDbHelper = ThreadsSQLiteHelper.getInstance(DBpreload.this);
            db = mDbHelper.getWritableDatabase();

            Intent intent = getIntent();
            creationTimeStamp = intent.getLongExtra(MainActivity.CREATION_TIME_STAMP, System.currentTimeMillis());

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(DBpreload.this);
            if(sharedPreferences.getString(DB_EXISTS, "false").equals("false")){    //check if db_exists according to shared preferences
                Log.i(TAG, sharedPreferences.getString(DB_EXISTS, "false"));
                preloadDB();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(DBpreload.DB_EXISTS, "true"); //
                editor.commit();
                Log.i(TAG, sharedPreferences.getString(DB_EXISTS, "true"));
            }

            finish();

        }





    //User IDS
    //7 is random
    //6 is preload
    private void preloadDB(){
        for(int i = 0; i < 9; i++){
            insertIntoDbFullDetail(preloadThread(i));
            Log.i("Preload","loaded number " + i);
        }
    }

    public long insertIntoDbFullDetail(Thread thread){

        ContentValues values = new ContentValues();
        values.put(ThreadsSQLiteHelper.COL_USER_ID, thread.getUser());
        values.put(ThreadsSQLiteHelper.COL_THR_TITLE, thread.getTitle());
        values.put(ThreadsSQLiteHelper.COL_KNOT, thread.getKnot());
        values.put(ThreadsSQLiteHelper.COL_KNOT_COUNT, thread.getKnot());
        values.put(ThreadsSQLiteHelper.COL_NODE, thread.getNode());
        values.put(ThreadsSQLiteHelper.COL_DATE_CRE, thread.getDateCreated());
        values.put(ThreadsSQLiteHelper.COL_DATE_MOD, thread.getDateModified());
        values.put(ThreadsSQLiteHelper.COL_ASSET_TYPES, thread.getAssetType());
        values.put(ThreadsSQLiteHelper.COL_ASSET_COUNT, thread.getAssetCount());
        values.put(ThreadsSQLiteHelper.COL_LAT, thread.getLat());
        values.put(ThreadsSQLiteHelper.COL_LON, thread.getLon());
        values.put(ThreadsSQLiteHelper.COL_DUR, thread.getDuration());
        values.put(ThreadsSQLiteHelper.COL_RATING, thread.getRating());
        values.put(ThreadsSQLiteHelper.COL_RATINGS, thread.getRatingCount());
        values.put(ThreadsSQLiteHelper.COL_LENGTH, thread.getLength());
        values.put(ThreadsSQLiteHelper.COL_VIEWS, thread.getViews());

        long newRowId = db.insert(ThreadsSQLiteHelper.THREADS_TABLE_NAME, null,values);
        return newRowId;
    }


    public static Thread preloadThread(int i){   //uses correct assets, duplicate below loads icons until picasso implemented
        Thread thread;
        switch (i) {
            case 0:
                thread = new Thread(-1, 6, "St Mary's Cathedral", -1, 1, -1,
                        creationTimeStamp-86400000*100, creationTimeStamp-86400000*100,
                        R.drawable.stmarynew, R.color.Color_ZeroAlpha,
                        37.784956f, -122.425486f, 222, 4.75f, 10000, -1, 19356);
                return thread;
            case 1:
                thread = new Thread(-1, 6, "Old St Mary's Cathedral", -1, 1, -1,
                        creationTimeStamp-86400000*400, creationTimeStamp-86400000*400,
                        R.drawable.stmaryold, R.color.Color_ZeroAlpha,
                        37.792605f, -122.405726f, 111, 3, 7653, -1, 7834);
                return thread;
            case 2:
                thread = new Thread(-1, 6, "Grace Cathedral", -1, 1, -1,
                        creationTimeStamp-86400000*15, creationTimeStamp-86400000*15,
                        R.drawable.gracecathedral, R.color.Color_ZeroAlpha,
                        37.791926f, -122.412982f, 199, 4.5f, 934, -1, 8934);
                return thread;
            case 3:
                thread = new Thread(-1, 6, "Ferry Building", -1, 1, -1,
                        creationTimeStamp-2592000, creationTimeStamp-2592000,
                        R.drawable.ferrybldgold, R.color.Color_ZeroAlpha,
                        37.795378f, -122.393552f, 154, 4f, 7833, -1, 9356);
                return thread;
            case 4:
                thread = new Thread(-1, 6, "Exploratorium", -1, 1, -1,
                        creationTimeStamp-1728000, creationTimeStamp-1728000,
                        R.drawable.exploratorium, R.color.Color_ZeroAlpha,
                        37.800656f, -122.398568f, 222, 3.5f, 982, -1, 5936);
                return thread;
            case 5:
                thread = new Thread(-1, 6, "Lombard St", -1, 1, -1,
                        creationTimeStamp-51840000, creationTimeStamp-51840000,
                        R.drawable.lombardst, R.color.Color_ZeroAlpha,
                        37.802204f, -122.418058f, 555, 4f, 3455, -1, 34343);
                return thread;
            case 6:
                thread = new Thread(-1, 6, "Cafe La Taza - Post St", -1, 1, -1,
                        creationTimeStamp-864000, creationTimeStamp-864000,
                        R.drawable.cafelataza, R.color.Color_ZeroAlpha,
                        37.788202f, -122.409575f, 60, 5f, 2403, -1, 5101);
                return thread;
            case 7:
                thread = new Thread(-1, 6, "Philz Cafe", -1, 1, -1,
                        creationTimeStamp-103680000, creationTimeStamp-103680000,
                        R.drawable.philz, R.color.Color_ZeroAlpha,
                        37.791655f, -122.398965f, 43, 4.25f, 234, -1, 3456);
                return thread;
            default:
                thread = new Thread(-1, 6, "Workshop Cafe", -1, 1, -1,
                        creationTimeStamp-86400000*9, creationTimeStamp-86400000*9,
                        R.drawable.workshop, R.color.Color_ZeroAlpha,
                        37.790673f, -122.402224f, 32, 4.5f, 45, -1, 2124);
                return thread;
        }
    }


//    public static Thread preloadThread(int i){
//        Thread thread;
//        switch (i) {
//            case 0:
//                thread = new Thread(6, "St Mary's Cathedral", -1, 1, -1,
//                        creationTimeStamp, creationTimeStamp,
//                        android.R.drawable.ic_menu_crop, R.color.Color_ZeroAlpha,
//                        37.784956f, -122.425486f, 222, 4.75f, 10000, -1, 19356);
//                return thread;
//            case 1:
//                thread = new Thread(6, "Old St Mary's Cathedral", -1, 1, -1,
//                        creationTimeStamp, creationTimeStamp,
//                        android.R.drawable.ic_menu_crop, R.color.Color_ZeroAlpha,
//                        37.792605f, -122.405726f, 111, 3, 7653, -1, 7834);
//                return thread;
//            case 2:
//                thread = new Thread(6, "Grace Cathedral", -1, 1, -1,
//                        creationTimeStamp, creationTimeStamp,
//                        android.R.drawable.ic_menu_crop, R.color.Color_ZeroAlpha,
//                        37.791926f, -122.412982f, 199, 4.5f, 934, -1, 8934);
//                return thread;
//            case 3:
//        thread = new Thread(6, "Ferry Building", -1, 1, -1,
//                creationTimeStamp, creationTimeStamp,
//                android.R.drawable.ic_menu_crop, R.color.Color_ZeroAlpha,
//                37.795378f, -122.393552f, 154, 4f, 7833, -1, 9356);
//                return thread;
//            case 4:
//        thread = new Thread(6, "Exploratorium", -1, 1, -1,
//                creationTimeStamp, creationTimeStamp,
//                android.R.drawable.ic_menu_crop, R.color.Color_ZeroAlpha,
//                37.800656f, -122.398568f, 222, 3.5f, 982, -1, 5936);
//                return thread;
//            case 5:
//        thread = new Thread(6, "Lombard St", -1, 1, -1,
//                creationTimeStamp, creationTimeStamp,
//                android.R.drawable.ic_menu_crop, R.color.Color_ZeroAlpha,
//                37.802204f, -122.418058f, 555, 4f, 3455, -1, 34343);
//                return thread;
//            case 6:
//        thread = new Thread(6, "Cafe La Taza - Post St", -1, 1, -1,
//                creationTimeStamp, creationTimeStamp,
//                android.R.drawable.ic_menu_crop, R.color.Color_ZeroAlpha,
//                37.788202f, -122.409575f, 60, 5f, 2403, -1, 5101);
//                return thread;
//            case 7:
//        thread = new Thread(6, "Philz Cafe", -1, 1, -1,
//                creationTimeStamp, creationTimeStamp,
//                android.R.drawable.ic_menu_crop, R.color.Color_ZeroAlpha,
//                37.791655f, -122.398965f, 43, 4.25f, 234, -1, 3456);
//                return thread;
//            default:
//        thread = new Thread(6, "Workshop Cafe", -1, 1, -1,
//                creationTimeStamp, creationTimeStamp,
//                android.R.drawable.ic_menu_crop, R.color.Color_ZeroAlpha,
//                37.790673f, -122.402224f, 32, 4.5f, 45, -1, 2124);
//                return thread;
//        }
//    }
}
