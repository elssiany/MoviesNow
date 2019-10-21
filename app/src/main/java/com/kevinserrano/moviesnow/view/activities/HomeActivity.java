package com.kevinserrano.moviesnow.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kevinserrano.moviesnow.BuildConfig;
import com.kevinserrano.moviesnow.R;
import com.kevinserrano.moviesnow.SQLite.dao.MovieDao;
import com.kevinserrano.moviesnow.model.Movie;
import com.kevinserrano.moviesnow.model.MoviePageResult;
import com.kevinserrano.moviesnow.restApi.EndpointsApi;
import com.kevinserrano.moviesnow.restApi.RetrofitInstance;
import com.kevinserrano.moviesnow.utilities.Constants;
import com.kevinserrano.moviesnow.utilities.ManagerSharedPreferences;
import com.kevinserrano.moviesnow.utilities.SystemUtils;
import com.kevinserrano.moviesnow.view.adapter.MovieAdapter;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {


    private MovieAdapter mFavoriteMoviesAdapter;
    private MovieAdapter mgetUpcomingMoviesMoviesAdapter;
    private MovieAdapter mPopularMoviesAdapter;
    private MovieAdapter mTopRatedMoviesAdapter;
    private MovieDao movieDao = new MovieDao();
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
    }


    private void initViews(){

        fab = findViewById(R.id.fab);
        setSupportActionBar(findViewById(R.id.toolbar));
        //Define recyclerViews Layout
        RecyclerView recyclerRecentMovies = findViewById(R.id.rvDataFilter1);
        recyclerRecentMovies.setLayoutManager(new LinearLayoutManager(this
                ,LinearLayoutManager.HORIZONTAL,false));
        RecyclerView recyclergetUpcomingMoviesMovies = findViewById(R.id.rvDataFilter2);
        recyclergetUpcomingMoviesMovies.setLayoutManager(new LinearLayoutManager(this
                ,LinearLayoutManager.HORIZONTAL,false));
        RecyclerView recyclerPopularMovies = findViewById(R.id.rvDataFilter3);
        recyclerPopularMovies.setLayoutManager(new LinearLayoutManager(this
                ,LinearLayoutManager.HORIZONTAL,false));
        RecyclerView recyclerTopRatedMovies = findViewById(R.id.rvDataFilter4);
        recyclerTopRatedMovies.setLayoutManager(new LinearLayoutManager(this
                ,LinearLayoutManager.HORIZONTAL,false));
        mFavoriteMoviesAdapter = new MovieAdapter(this, Constants.TYPE_VIEW_RECEPT_MOVIES);
        mgetUpcomingMoviesMoviesAdapter = new MovieAdapter(this, Constants.TYPE_VIEW_MOVIES);
        mPopularMoviesAdapter = new MovieAdapter(this, Constants.TYPE_VIEW_MOVIES);
        mTopRatedMoviesAdapter = new MovieAdapter(this, Constants.TYPE_VIEW_MOVIES);
        recyclerRecentMovies.setAdapter(mFavoriteMoviesAdapter);
        recyclergetUpcomingMoviesMovies.setAdapter(mgetUpcomingMoviesMoviesAdapter);
        recyclerPopularMovies.setAdapter(mPopularMoviesAdapter);
        recyclerTopRatedMovies.setAdapter(mTopRatedMoviesAdapter);

        ((NestedScrollView) findViewById(R.id.nestedScrollView))
                .setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener)
                        (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                    if(scrollY > 600)
                        fab.hide();
                    if (scrollY < 200)
                        fab.show();
                });

        SystemUtils.getInstance().validateExpireData();

        if(ManagerSharedPreferences.getPreferences("canGetData",true)){
            if(SystemUtils.getInstance().isNetworkAvailable(this)){
                loadDatas();
            } else {
                Toast.makeText(getApplicationContext(),getString(R.string.message_error_1),
                        Toast.LENGTH_LONG).show();
            }
        }else {
           loadLocalDatas();
        }

    }


    public void onClick(View view){
        startActivity(new Intent(this,SearchActivity.class));
    }


    private void loadLocalDatas() {

        mFavoriteMoviesAdapter.crearAll();
        mFavoriteMoviesAdapter.addMovies(movieDao.getMovies("SELECT * FROM movies WHERE is_favorite='1'"));
        mgetUpcomingMoviesMoviesAdapter.crearAll();
        mgetUpcomingMoviesMoviesAdapter.addMovies(movieDao.getMovies("SELECT * FROM movies WHERE section='1'"));
        mPopularMoviesAdapter.crearAll();
        mPopularMoviesAdapter.addMovies(movieDao.getMovies("SELECT * FROM movies WHERE section='2'"));
        mTopRatedMoviesAdapter.crearAll();
        mTopRatedMoviesAdapter.addMovies(movieDao.getMovies("SELECT * FROM movies WHERE section='3'"));

    }


    private void loadDatas() {

        EndpointsApi movieDataService = RetrofitInstance.getRetrofitInstance().create(EndpointsApi.class);
        Call<MoviePageResult> callgetUpcomingMoviesMovies = movieDataService.getUpcomingMovies(1, BuildConfig.KeyAPIMovies);
        Call<MoviePageResult> callPopularMovies = movieDataService.getPopularMovies(1, BuildConfig.KeyAPIMovies);
        Call<MoviePageResult> callTopRatedMovies = movieDataService.getTopRatedMovies(1, BuildConfig.KeyAPIMovies);
        callgetUpcomingMoviesMovies.enqueue(new Callback<MoviePageResult>() {
            @Override
            public void onResponse(@NonNull Call<MoviePageResult> call, @NonNull Response<MoviePageResult> response) {
                assert response.body() != null;
                mgetUpcomingMoviesMoviesAdapter.addMovies(response.body().getMovieResult());
                for(Movie movie: response.body().getMovieResult()){
                    movie.setMovieId(movie.getId());
                    movie.setSection("1");
                    movieDao.saveMovie(movie);
                }
                ManagerSharedPreferences
                        .editPreferences("timerGetData", new Date().getTime()+(12*3600000L));
            }

            @Override
            public void onFailure(@NonNull Call<MoviePageResult> call, @NonNull Throwable t) {
                Toast.makeText(HomeActivity.this, R.string.message_error_2,
                        Toast.LENGTH_SHORT).show();
            }
        });
        callPopularMovies.enqueue(new Callback<MoviePageResult>() {
            @Override
            public void onResponse(@NonNull Call<MoviePageResult> call, @NonNull Response<MoviePageResult> response) {
                assert response.body() != null;
                mPopularMoviesAdapter.addMovies(response.body().getMovieResult());
                for(Movie movie: response.body().getMovieResult()){
                    movie.setMovieId(movie.getId());
                    movie.setSection("2");
                    movieDao.saveMovie(movie);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviePageResult> call, @NonNull Throwable t) {
                Toast.makeText(HomeActivity.this, R.string.message_error_2,
                        Toast.LENGTH_SHORT).show();
            }
        });
        callTopRatedMovies.enqueue(new Callback<MoviePageResult>() {
            @Override
            public void onResponse(@NonNull Call<MoviePageResult> call, @NonNull Response<MoviePageResult> response) {
                assert response.body() != null;
                mTopRatedMoviesAdapter.addMovies(response.body().getMovieResult());
                for(Movie movie: response.body().getMovieResult()){
                    movie.setMovieId(movie.getId());
                    movie.setSection("3");
                    movieDao.saveMovie(movie);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviePageResult> call, @NonNull Throwable t) {
                Toast.makeText(HomeActivity.this, R.string.message_error_2,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_settings){
            startActivity(new Intent(this,SettingsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        if(ManagerSharedPreferences.getPreferences("updateFavoritesItems",false)){
            mFavoriteMoviesAdapter.crearAll();
            mFavoriteMoviesAdapter.addMovies(movieDao.getMovies("SELECT * FROM movies WHERE is_favorite='1'"));
        }
        super.onResume();
    }


}
