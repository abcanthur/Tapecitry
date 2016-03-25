package com.example.petermartinez.tapecitry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class ThreadsSQLiteHelper extends SQLiteOpenHelper implements BaseColumns{
    private static final String TAG = ThreadsSQLiteHelper.class.getCanonicalName();

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TAPECITRY_DB";
    public static final String THREADS_TABLE_NAME = "THREADS";

    public static final String COL_ID = "_id";
    public static final String COL_USER_ID = "USER_ID";
    public static final String COL_THR_TITLE = "THR_TITLE";
    public static final String COL_KNOT = "KNOT";
    public static final String COL_KNOT_COUNT = "KNOT_COUNT";
    public static final String COL_NODE = "NODE";
    public static final String COL_DATE_CRE = "DATE_CRE";
    public static final String COL_DATE_MOD = "DATE_MOD";
    public static final String COL_ASSET_TYPES = "ASSET_TYPES";
    public static final String COL_ASSET_COUNT = "ASSET_COUNT";
    public static final String COL_LAT = "LAT";
    public static final String COL_LON = "LON";
    public static final String COL_DUR = "DUR";
    public static final String COL_RATING = "RATING"; //0-5
    public static final String COL_RATINGS = "RATINGS"; //how many times rated, to update rating correctly
    public static final String COL_LENGTH = "LENGTH";
    public static final String COL_VIEWS = "VIEWS";

    public static final String[] THREADS_COLUMNS = {COL_ID, COL_USER_ID,COL_THR_TITLE,COL_KNOT,COL_KNOT_COUNT,COL_NODE,COL_DATE_CRE,COL_DATE_MOD,COL_ASSET_TYPES,COL_ASSET_COUNT,COL_LAT,COL_LON,COL_DUR,COL_RATING,COL_RATINGS,COL_LENGTH,COL_VIEWS};

    private static ThreadsSQLiteHelper instance;



    private static final String CREATE_THREADS_TABLE =
            "CREATE TABLE " + THREADS_TABLE_NAME +
                    "(" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_USER_ID + " INTEGER, " +
                    COL_THR_TITLE + " TEXT, " +
                    COL_KNOT + " INTEGER, " +
                    COL_KNOT_COUNT + " INTEGER, " +
                    COL_NODE + " INTEGER, " +
                    COL_DATE_CRE + " LONG, " +
                    COL_DATE_MOD + " LONG, " +
                    COL_ASSET_TYPES + " INTEGER, " +
                    COL_ASSET_COUNT + " INTEGER, " +
                    COL_LAT + " FLOAT, " +
                    COL_LON + " FLOAT, " +
                    COL_DUR + " INTEGER, " +
                    COL_RATING + " FLOAT, " +
                    COL_RATINGS + " INTEGER, " +
                    COL_LENGTH + " INTEGER, " +
                    COL_VIEWS + " INTEGER )";


    public static ThreadsSQLiteHelper getInstance(Context context){
        if(instance == null){
            instance = new ThreadsSQLiteHelper(context);
        }
        return instance;
    }

    public ThreadsSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_THREADS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + THREADS_TABLE_NAME);
        this.onCreate(db);
    }


    public Thread newRandomThread(String newTitle){
        if(newTitle.equals("")){
            newTitle = getRandomName();
        }
        Thread newThread = new Thread(newTitle);
        return newThread;
    }



    public String getRandomName(){
        String[] article = {"The", "El", "La", "Los", "Las", "The", "The", "The", "A", "A", "" , "" , "", "" ,"",""};
        String[] place = {"Mission", "Valencia", "Coit", "Gold Rush", "Google", "Market", "Alcatraz", "Marin", "Potrero", "", "", "","",""};
        String[] thing = {"Cafe", "Taqueria", "Building", "Bistro", "Memorial", "Festival", "Massacre", "Battle", "Concert", "Tower", "Company", "","","","","",""};
        String name = "";
        int temp = (int) (Math.random()*article.length);
        name = name + article[temp];
        temp = (int) (Math.random()*place.length);
        name = name + " " + place[temp];
        temp = (int) (Math.random()*thing.length);
        name = name + " " + thing[temp];
        if(name.equals("  ")){
            return "Cafe La Taza";
        }
        return name;
    }

    public Cursor getThreadsById(String[] query){

        SQLiteDatabase db = this.getReadableDatabase();
//        String[] idTitleLatLon = {COL_ID, COL_THR_TITLE, COL_ASSET_TYPES};
//
//        Cursor cursor = db.query(THREADS_TABLE_NAME, // a. table
//                idTitleLatLon, // b. column names
//                COL_ID + " LIKE ?", // c. selections
//                new String[]{"%" + query + "%"}, // d. selections args
//                null, // e. group by
//                null, // f. having
//                null, // g. order by
//                null); // h. limit

        Cursor cursor = db.rawQuery("SELECT " +
                COL_ID + "," +
        COL_THR_TITLE + "," +
        COL_LAT + "," +
        COL_LON + " FROM " +
        THREADS_TABLE_NAME + " WHERE " +
        COL_ID + " = ",
        query);

        return cursor;
    }

    public Cursor searchThreads(String query){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(THREADS_TABLE_NAME, // a. table
                THREADS_COLUMNS, // b. column names
                COL_THR_TITLE + " LIKE ?", // c. selections
                new String[]{"%" + query + "%"}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        return cursor;
    }
}
