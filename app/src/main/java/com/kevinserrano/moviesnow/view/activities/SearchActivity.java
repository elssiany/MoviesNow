package com.kevinserrano.moviesnow.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.kevinserrano.moviesnow.R;
import com.kevinserrano.moviesnow.SQLite.dao.MovieDao;
import com.kevinserrano.moviesnow.model.Movie;
import com.kevinserrano.moviesnow.view.adapter.MovieSearchResultAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {


    private MovieSearchResultAdapter mMovieSearchResultAdapter;
    private List<Movie> mMovies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initViews();
    }



    private void initViews(){

        EditText inputText = findViewById(R.id.inputText);
        mMovieSearchResultAdapter = new MovieSearchResultAdapter(this);
        RecyclerView rvMovieSearchResult = findViewById(R.id.rvMovieSearchResult);
        rvMovieSearchResult.setLayoutManager(new LinearLayoutManager(this
                ,LinearLayoutManager.VERTICAL,false));
        rvMovieSearchResult.setAdapter(mMovieSearchResultAdapter);

        inputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                if(text.length() > 0) {
                   searchMovies(text);
                } else {
                    mMovieSearchResultAdapter.crearAll();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mMovies = new MovieDao().getMovies("SELECT * FROM movies");

    }


    private void searchMovies(String query){
        mMovieSearchResultAdapter.crearAll();
        for(Movie movie: mMovies){
            if(movie.getOriginalTitle().contains(query)) {
                mMovieSearchResultAdapter.addMovie(movie);
            }
        }
    }


}
