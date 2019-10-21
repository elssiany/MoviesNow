package com.kevinserrano.moviesnow.view.adapter;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kevinserrano.moviesnow.R;
import com.kevinserrano.moviesnow.model.MovieTrailer;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by kevin
 */
public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.MovieTrailerViewHolder> {


    private LayoutInflater ensamblador;

    // An Activity's Context.
    private final Activity mActivity;

    // The list of movies.
    private List<MovieTrailer> mMovieTrailers = new ArrayList<>();

    public MovieTrailerAdapter(final Activity mActivity) {
        this.mActivity = mActivity;
        this.ensamblador = LayoutInflater.from(this.mActivity);
    }

    class MovieTrailerViewHolder extends RecyclerView.ViewHolder {

         TextView txtTitle;
         View itemView;
        MovieTrailerViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            this.itemView = itemView;
         }

    }

    @NonNull
    @Override
    public MovieTrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieTrailerViewHolder(ensamblador.inflate(R.layout.item_movie_trailer, parent, false));
    }



    @Override
    public void onBindViewHolder(@NonNull final MovieTrailerViewHolder holder, final int position) {

        final MovieTrailer movieTrailer = mMovieTrailers.get(
                holder.getAdapterPosition());

        holder.txtTitle.setText(movieTrailer.getName());

        holder.itemView.setOnClickListener(view -> {
            mActivity.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" +
                    movieTrailer.getKey())));
        });

    }


   @Override
    public int getItemCount() {
        return mMovieTrailers.size();
    }


    public void addTrailers(List<MovieTrailer> mMovieTrailers){
        this.mMovieTrailers = mMovieTrailers;
        notifyDataSetChanged();
    }


}
