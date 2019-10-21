package com.kevinserrano.moviesnow.utilities;

import android.content.SharedPreferences;

import com.kevinserrano.moviesnow.MoviesNowApp;


public class ManagerSharedPreferences {


    private static final String MY_PREFERENCE = "data_moviesnow";


    public static void editPreferences(final String idPreference, final int dato) {

        SharedPreferences.Editor editor = MoviesNowApp.getInstance().getSharedPreferences(MY_PREFERENCE,0).edit();

        editor.putInt(idPreference,dato);
        editor.apply();


    }

    public static void editPreferences(String idPreference, String dato) {

        SharedPreferences.Editor editor = MoviesNowApp.getInstance().getSharedPreferences(MY_PREFERENCE,0).edit();

        editor.putString(idPreference,dato);
        editor.apply();


    }

    public static void editPreferences(String idPreference, long dato) {

        SharedPreferences.Editor editor = MoviesNowApp.getInstance().getSharedPreferences(MY_PREFERENCE,0).edit();

        editor.putLong(idPreference,dato);
        editor.apply();


    }

    public static void editPreferences(String idPreference, boolean dato) {

        SharedPreferences.Editor editor = MoviesNowApp.getInstance().getSharedPreferences(MY_PREFERENCE,0).edit();
        editor.putBoolean(idPreference,dato);
        editor.apply();

    }


    public static int getPreferences(final String  idPreference,final int dato) {

       return MoviesNowApp.getInstance().getSharedPreferences(MY_PREFERENCE, 0)
                .getInt(idPreference, dato);

    }



    public static long getPreferences(final String  idPreference,final long dato) {

        return MoviesNowApp.getInstance().getSharedPreferences(MY_PREFERENCE, 0)
                .getLong(idPreference, dato);
    }


    public static boolean getPreferences(String  idPreference,boolean dato) {


       return MoviesNowApp.getInstance().getSharedPreferences(MY_PREFERENCE, 0)
               .getBoolean(idPreference,dato);

    }


    public static String getPreferences( String  idPreference, String dato) {

        return MoviesNowApp.getInstance().getSharedPreferences(MY_PREFERENCE, 0)
                .getString(idPreference, dato);
    }




}
