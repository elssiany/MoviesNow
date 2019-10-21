package com.kevinserrano.moviesnow.SQLite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by kevin
 */

public class MovieDbHelper extends SQLiteOpenHelper {


    private static final String TAG = "ManagerSqlHelper";
    private final  static String dataBaseName = "MoviesNowApp";
    private final static int version = 1;

    private final static String createMovies = "CREATE TABLE movies" + "(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "movie_id INTEGER," +
            "section VARCHAR(100)," +
            "vote_average DOUBLE," +
            "original_title VARCHAR(100)," +
            "backdrop_path VARCHAR(200)," +
            "overview VARCHAR(1000)," +
            "release_date VARCHAR(100), "+
            "is_favorite VARCHAR(2)," +
            "poster_path VARCHAR(200)"+")";

    public MovieDbHelper(Context context) {
        super(context, dataBaseName, null, version);
    }


    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createMovies);
    }



    //https://riggaroo.co.za/android-sqlite-database-use-onupgrade-correctly/
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.d(TAG, "Updating table from " + oldVersion + " to " + newVersion);
    }




}
