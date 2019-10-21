package com.kevinserrano.moviesnow.SQLite.dao;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kevinserrano.moviesnow.MoviesNowApp;
import com.kevinserrano.moviesnow.SQLite.db.MovieDbHelper;
import com.kevinserrano.moviesnow.model.Movie;

import java.util.ArrayList;
import java.util.List;


public class MovieDao {

    private final String NAME_TABLE ="movies";
    private final String ID ="id";
    private final String MOVIE_ID ="movie_id";
    private final String SECTION ="section";
    private final String ORIGINAL_TITLE ="original_title";
    private final String OVERVIEW ="overview";
    private final String BACKDROP_PATH="backdrop_path";
    private final String POSTER_PATH="poster_path";
    private final String VOTE_AVERAGE="vote_average";
    private final String RELEASE_DATE="release_date";
    private final String IS_FAVORITE ="is_favorite";


    public List<Movie> getMovies(String query){

        SQLiteDatabase db = new MovieDbHelper(MoviesNowApp.getInstance()).getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        List<Movie> list = new ArrayList<>();
        Movie movie;
        while(c.moveToNext()) {//c.moveToFirst()
            movie = new Movie();
            movie.setId(c.getInt(c.getColumnIndex(ID)));
            movie.setMovieId(c.getInt(c.getColumnIndex(MOVIE_ID)));
            movie.setSection(c.getString(c.getColumnIndex(SECTION)));
            movie.setOriginalTitle(c.getString(c.getColumnIndex(ORIGINAL_TITLE)));
            movie.setOverview(c.getString(c.getColumnIndex(OVERVIEW)));
            movie.setPosterPath(c.getString(c.getColumnIndex(POSTER_PATH)));
            movie.setBackdropPath(c.getString(c.getColumnIndex(BACKDROP_PATH)));
            movie.setReleaseDate(c.getString(c.getColumnIndex(RELEASE_DATE)));
            movie.setVoteAverage(c.getDouble(c.getColumnIndex(VOTE_AVERAGE)));
            movie.setFavorite(c.getString(c.getColumnIndex(IS_FAVORITE)));
            list.add(movie);
        }

        db.close();
        c.close();
        return list;
    }



    public void deleteMovies(){
        SQLiteDatabase db = new MovieDbHelper(MoviesNowApp.getInstance()).getWritableDatabase();
        db.execSQL("DELETE FROM "+NAME_TABLE); //delete all rows in a table
        //cierro la base de datos
        db.close();
    }



    public boolean saveMovie(Movie movie){
        long inserted = 0;
        SQLiteDatabase db = new MovieDbHelper(MoviesNowApp.getInstance()).getWritableDatabase();
        if (db!=null){
            ContentValues newItem = new ContentValues();
            newItem.put(ID, movie.getId());
            newItem.put(MOVIE_ID,movie.getMovieId());
            newItem.put(SECTION,movie.getSection());
            newItem.put(ORIGINAL_TITLE, movie.getOriginalTitle());
            newItem.put(OVERVIEW, movie.getOverview());
            newItem.put(POSTER_PATH, movie.getPosterPath());
            newItem.put(BACKDROP_PATH, movie.getBackdropPath());
            newItem.put(RELEASE_DATE, movie.getReleaseDate());
            newItem.put(VOTE_AVERAGE, movie.getVoteAverage());
            newItem.put(IS_FAVORITE, movie.isFavorite());
            inserted = db.insert(NAME_TABLE, null, newItem);
            db.close();
        }

        return inserted > 0;

    }


    public boolean updateMovie(Movie movie){
        int  updated=0;
        SQLiteDatabase db = new MovieDbHelper(MoviesNowApp.getInstance()).getWritableDatabase();
        if (db!=null){
            ContentValues newItem = new ContentValues();
            newItem.put(ID, movie.getId());
            newItem.put(MOVIE_ID,movie.getMovieId());
            newItem.put(SECTION,movie.getSection());
            newItem.put(ORIGINAL_TITLE, movie.getOriginalTitle());
            newItem.put(OVERVIEW, movie.getOverview());
            newItem.put(POSTER_PATH, movie.getPosterPath());
            newItem.put(BACKDROP_PATH, movie.getBackdropPath());
            newItem.put(RELEASE_DATE, movie.getReleaseDate());
            newItem.put(VOTE_AVERAGE, movie.getVoteAverage());
            newItem.put(IS_FAVORITE, movie.isFavorite());
            updated=db.update(NAME_TABLE, newItem,"movie_id='"+movie.getMovieId()+"'", null);
            db.close();
        }

        return updated>0;
    }


}
