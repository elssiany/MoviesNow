package com.kevinserrano.moviesnow.utilities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.widget.Toast;

import com.github.thunder413.datetimeutils.DateTimeUnits;
import com.github.thunder413.datetimeutils.DateTimeUtils;
import com.kevinserrano.moviesnow.SQLite.dao.MovieDao;

import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by kevin
 */
public class SystemUtils {


    /**
     * Singleton instance
     */
    private static final SystemUtils INSTANCE = new SystemUtils();

    /**
     * Get the singleton instance of this class
     * @return the unique instance of this class
     */
    public static SystemUtils getInstance() {
        return INSTANCE;
    }

    public int numRandom(int desde,int hasta){
        return (int) Math.floor(Math.random()*(hasta-desde+1)+desde);
    }


    /**
     *
     * @return yyyy-MM-dd HH:mm:ss formate date as string
     */
    public String getCurrentTimeStamp(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(new Date()); // Find todays date
    }


    public String getCurrentTimeStamp(long dateInMillis){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            return dateFormat.format(new Date(dateInMillis)); // Find todays date
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    public Date getDate(long dateInMillis){
        //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        return  new Date(dateInMillis);
    }


    public  String imagePathBuilder(String fileSize,String imagePath) {
        return "https://image.tmdb.org/t/p/" +
                fileSize +
                imagePath;
    }


    public boolean isNetworkAvailable(Context context) {
        boolean enabled = true;
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();

        if((info == null || !info.isConnected() || !info.isAvailable())) {
            enabled = false;
        }
        return enabled;
    }


    public  void validateExpireData(){

        Calendar calendarEnd = Calendar.getInstance();

        calendarEnd.setTimeInMillis(ManagerSharedPreferences
                .getPreferences("timerGetData", 7666L));

        long timerFinal = DateTimeUtils.getDateDiff(calendarEnd.getTime(),
                new Date(), DateTimeUnits.HOURS); //timerEnd-timerInit

        long seconds = DateTimeUtils.getDateDiff(calendarEnd.getTime(),
                new Date(), DateTimeUnits.SECONDS);

        timerFinal = timerFinal*3600000L;

        timerFinal+=seconds*1000;

        if(timerFinal > 0){

            ManagerSharedPreferences.editPreferences(
                    "canGetData",false);

        }else{
            if(ManagerSharedPreferences
                    .getPreferences("timerGetData", 7666L)!=7666L){
                new MovieDao().deleteMovies();
            }
            ManagerSharedPreferences.editPreferences(
                    "canGetData",true);
        }

    }

}
