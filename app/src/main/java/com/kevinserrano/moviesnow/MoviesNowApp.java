
package com.kevinserrano.moviesnow;

import android.app.Application;
import android.content.Context;


public class MoviesNowApp extends Application {


    private static MoviesNowApp sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }



    public static MoviesNowApp getInstance() {
        if (sInstance == null) {
            sInstance = new MoviesNowApp();
        }
        return sInstance;
    }


    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //MultiDex.install(this);
    }

}