package com.kevinserrano.moviesnow.view.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kevinserrano.moviesnow.BuildConfig;
import com.kevinserrano.moviesnow.R;
import com.kevinserrano.moviesnow.SQLite.dao.MovieDao;
import com.kevinserrano.moviesnow.model.Movie;
import com.kevinserrano.moviesnow.model.MovieTrailer;
import com.kevinserrano.moviesnow.model.MovieTrailerPageResult;
import com.kevinserrano.moviesnow.restApi.EndpointsApi;
import com.kevinserrano.moviesnow.restApi.RetrofitInstance;
import com.kevinserrano.moviesnow.utilities.ManagerSharedPreferences;
import com.kevinserrano.moviesnow.utilities.SystemUtils;
import com.kevinserrano.moviesnow.view.adapter.MovieTrailerAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity {


    private Movie mMovie;
    private ArrayList<MovieTrailer> mMovieTrailers;
    private MovieTrailerAdapter mMovieTrailerAdapter;
    private FloatingActionButton fab;
    private ContentLoadingProgressBar contentLoadingProgressBar;
    private MovieDao movieDao = new MovieDao();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        initViews(savedInstanceState);
    }


    private void initViews(Bundle savedInstanceState){

        ImageView poster = findViewById(R.id.img);
        contentLoadingProgressBar = findViewById(R.id.loadingProgressBar);
        fab = findViewById(R.id.fab);
        RecyclerView recyclerTrailers = findViewById(R.id.rvMovieTrailes);
        recyclerTrailers.setLayoutManager(new LinearLayoutManager(this
                ,LinearLayoutManager.VERTICAL,false));
        mMovieTrailerAdapter = new MovieTrailerAdapter(this);
        recyclerTrailers.setAdapter(mMovieTrailerAdapter);
        if(savedInstanceState == null) {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            assert bundle != null;
            mMovie = (Movie) bundle.getSerializable("movie");
            if(SystemUtils.getInstance().isNetworkAvailable(this)){
                getTrailers(mMovie.getId());
            }
        } else {
            mMovie = (Movie) savedInstanceState.getSerializable("movie");
            if(SystemUtils.getInstance().isNetworkAvailable(this)){
                mMovieTrailers = (ArrayList<MovieTrailer>) savedInstanceState.getSerializable("movieTrailers");
                mMovieTrailerAdapter.addTrailers(mMovieTrailers);
            }
        }

        if(mMovie.isFavorite().equals("1"))
            fab.setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.ic_favorite_white));
        else
            fab.setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.ic_favorite_border_white));

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            poster.setTransitionName(mMovie.getOriginalTitle());
        }

        supportPostponeEnterTransition();

        Glide.with(this)
                .load(SystemUtils.getInstance().imagePathBuilder("w200",
                        mMovie.getPosterPath()))
                .centerCrop()
                .dontAnimate()
                .apply(RequestOptions.placeholderOf(R.drawable.art_2))
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(34)))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e,
                                                Object model, Target<Drawable> target,
                                                boolean isFirstResource) {
                        supportStartPostponedEnterTransition();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model,
                                                   Target<Drawable> target,
                                                   DataSource dataSource, boolean isFirstResource) {
                        supportStartPostponedEnterTransition();
                        return false;
                    }
                })
                .into(poster);

        Glide.with(this)
                .load(SystemUtils.getInstance().imagePathBuilder("w500",
                        mMovie.getBackdropPath()))
                .into(((ImageView) findViewById(R.id.image)));

        ((TextView) findViewById(R.id.txtRating)).setText(String.format("%s/10 Rating",
                mMovie.getVoteAverage()));
        ((TextView) findViewById(R.id.txtTitle)).setText(mMovie.getOriginalTitle());
        ((TextView) findViewById(R.id.txtDate)).setText(mMovie.getReleaseDate());
        ((TextView) findViewById(R.id.txtOverview)).setText(mMovie.getOverview());

    }


    private void onShowLoadingProgressBar() {
        if(contentLoadingProgressBar!=null){
            contentLoadingProgressBar.show();
            contentLoadingProgressBar.setVisibility(View.VISIBLE);
        }
    }



    private void onHideLoadingProgressBar() {
        if(contentLoadingProgressBar!=null){
            contentLoadingProgressBar.hide();
            contentLoadingProgressBar.setVisibility(View.GONE);
        }
    }


    private void getTrailers(int movieId){

        onShowLoadingProgressBar();
        EndpointsApi movieDataService = RetrofitInstance.getRetrofitInstance().create(EndpointsApi.class);
        Call<MovieTrailerPageResult> call = movieDataService.getTrailers(movieId,
                BuildConfig.KeyAPIMovies);
        call.enqueue(new Callback<MovieTrailerPageResult>() {
            @Override
            public void onResponse(@NonNull Call<MovieTrailerPageResult> call, @NonNull
                    Response<MovieTrailerPageResult> response) {
                onHideLoadingProgressBar();
                assert response.body() != null;
                mMovieTrailers = response.body().getTrailerResult();
                mMovieTrailerAdapter.addTrailers(mMovieTrailers);
            }

            @Override
            public void onFailure(@NonNull Call<MovieTrailerPageResult> call, @NonNull Throwable t) {
                onHideLoadingProgressBar();
                Toast.makeText(MovieDetailsActivity.this, R.string.message_error_2,
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnExit:
                finish();
                break;
            case R.id.fab:
                if(mMovie.isFavorite().equals("1")) {
                    mMovie.setFavorite("0");
                }else {
                    mMovie.setFavorite("1");
                }
                saveMovieToFavorites();
                break;
        }
    }

    public void saveMovieToFavorites(){
        if(movieDao.updateMovie(mMovie)) {
            ManagerSharedPreferences.editPreferences("updateFavoritesItems",true);
            if(mMovie.isFavorite().equals("1")) {
                fab.setImageDrawable(ContextCompat.getDrawable(this,
                        R.drawable.ic_favorite_white));
                Toast.makeText(this,"Agregado a favoritos",Toast.LENGTH_SHORT).show();
            }else {
                fab.setImageDrawable(ContextCompat.getDrawable(this,
                        R.drawable.ic_favorite_border_white));
                Toast.makeText(this,"Removido de favoritos",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this,R.string.message_error_2,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("movie", mMovie);
        if(SystemUtils.getInstance().isNetworkAvailable(this)){
            outState.putSerializable("movieTrailers", mMovieTrailers);
        }
    }

}
