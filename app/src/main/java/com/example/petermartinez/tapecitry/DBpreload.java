package com.example.petermartinez.tapecitry;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by petermartinez on 3/23/16.
 */
public class DBpreload extends AppCompatActivity {
    public static final String DB_EXISTS = "false";


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            ThreadsSQLiteHelper mDbHelper = new ThreadsSQLiteHelper(DBpreload.this);
            SQLiteDatabase db = mDbHelper.getWritableDatabase();




            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(DBpreload.this);
            if(sharedPreferences.getString(DB_EXISTS, "false").equals("false")){
                Log.i("sharedPrefs", sharedPreferences.getString(DB_DOES_NOT_EXIST, "false"));
                preloadDB();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(MainActivity.DB_DOES_NOT_EXIST, "false");
                editor.commit();
                Log.i("sharedPrefs", sharedPreferences.getString(DB_DOES_NOT_EXIST, "true"));
            }
            threadArrayList.clear();
            dumpCursorToArray();

        }


    //User IDS
    //7 is random
    //6 is preload
    private void preloadDB(){
        for(int i = 0; i < 9; i++){
            insertIntoDbFullDetail(DBpreload.preload(i));
            Log.i("Preload","loaded number " + i);
        }
        threadArrayList.clear();
        dumpCursorToArray();
        mThreadAdapter.notifyDataSetChanged();
    }

    private long insertIntoDbFullDetail(String[] string){
        ContentValues values = new ContentValues();
        values.put(ThreadsSQLiteHelper.COL_USER_ID, string[0]);
        values.put(ThreadsSQLiteHelper.COL_THR_TITLE, string[1]);
        values.put(ThreadsSQLiteHelper.COL_KNOT, string[2]);
        values.put(ThreadsSQLiteHelper.COL_KNOT_COUNT, string[3]);
        values.put(ThreadsSQLiteHelper.COL_NODE, string[4]);
        values.put(ThreadsSQLiteHelper.COL_DATE_CRE, string[5]);
        values.put(ThreadsSQLiteHelper.COL_DATE_MOD, string[6]);
        values.put(ThreadsSQLiteHelper.COL_ASSET_TYPES, string[7]);
        values.put(ThreadsSQLiteHelper.COL_ASSET_COUNT, string[8]);
        values.put(ThreadsSQLiteHelper.COL_LAT, string[9]);
        values.put(ThreadsSQLiteHelper.COL_LON, string[10]);
        values.put(ThreadsSQLiteHelper.COL_DUR, string[11]);
        values.put(ThreadsSQLiteHelper.COL_RATING, string[12]);
        values.put(ThreadsSQLiteHelper.COL_RATINGS, string[13]);
        values.put(ThreadsSQLiteHelper.COL_LENGTH, string[14]);
        values.put(ThreadsSQLiteHelper.COL_VIEWS, string[15]);

        long newRowId = db.insert(ThreadsSQLiteHelper.THREADS_TABLE_NAME, null,values);
        return newRowId;
    }





    public static String preload(int i){
        ThreadsSQLiteHelper mDbHelper = new ThreadsSQLiteHelper(MainActivity.this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
    }

    public static String[] preload(int i) {
        switch (i) {
            case 0:
                String[] temp0 = {"6", "St Mary's Cathedral", "-1", "1", "-1", String.valueOf(System.currentTimeMillis()), String.valueOf(System.currentTimeMillis()), "R.drawable.stmarynew", "R.color.Color_ZeroAlpha", "37.784956", "-122.425486", "222", "4.75", "10000", "-1", "19356"};
                return temp0;
            case 1:
                String[] temp1 = {"6", "Old St Mary's Cathedral", "-1", "1", "-1", String.valueOf(System.currentTimeMillis()), String.valueOf(System.currentTimeMillis()), "R.drawable.stmaryold", "R.color.Color_ZeroAlpha", "37.792605", "-122.405726", "111", "3", "7653", "-1", "7834"};
                return temp1;
            case 2:
                String[] temp2 = {"6", "Grace Cathedral", "-1", "1", "-1", String.valueOf(System.currentTimeMillis()), String.valueOf(System.currentTimeMillis()), "R.drawable.gracecathedral", "R.color.Color_ZeroAlpha", "37.791926", "-122.412982", "199", "4.5", "934", "-1", "8934"};
                return temp2;
            case 3:
                String[] temp3 = {"6", "Ferry Building", "-1", "1", "-1", String.valueOf(System.currentTimeMillis()), String.valueOf(System.currentTimeMillis()), "R.drawable.ferrybldgold", "R.color.Color_ZeroAlpha", "37.795378", "-122.393552", "154", "4", "7833", "-1", "9356"};
                return temp3;
            case 4:
                String[] temp4 = {"6", "Exploratorium", "-1", "1", "-1", String.valueOf(System.currentTimeMillis()), String.valueOf(System.currentTimeMillis()), "R.drawable.exploratorium", "R.color.Color_ZeroAlpha", "37.800656", "-122.398568", "222", "3.5", "982", "-1", "5936"};
                return temp4;
            case 5:
                String[] temp5 = {"6", "Lombard St", "-1", "1", "-1", String.valueOf(System.currentTimeMillis()), String.valueOf(System.currentTimeMillis()), "R.drawable.lombardst", "R.color.Color_ZeroAlpha", "37.802204", "-122.418058", "555", "4", "3455", "-1", "34343"};
                return temp5;
            case 6:
                String[] temp6 = {"6", "Cafe La Taza - Post St", "-1", "1", "-1", String.valueOf(System.currentTimeMillis()), String.valueOf(System.currentTimeMillis()), "R.drawable.cafelataza", "R.color.Color_ZeroAlpha", "37.788202", "-122.409575", "60", "5", "2403", "-1", "5101"};
                return temp6;
            case 7:
                String[] temp7 = {"6", "Philz Cafe", "-1", "1", "-1", String.valueOf(System.currentTimeMillis()), String.valueOf(System.currentTimeMillis()), "R.drawable.philz", "R.color.Color_ZeroAlpha", "37.791655", "-122.398965", "43", "4.25", "234", "-1", "3456"};
                return temp7;
            default:
                String[] temp8 = {"6", "Workshop Cafe", "-1", "1", "-1", String.valueOf(System.currentTimeMillis()), String.valueOf(System.currentTimeMillis()), "R.drawable.workshop", "R.color.Color_ZeroAlpha", "37.790673", "-122.402224", "32", "4.5", "45", "-1", "2124"};
                return temp8;
        }
    }
}
