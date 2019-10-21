package com.kevinserrano.moviesnow.view.adapter;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.kevinserrano.moviesnow.R;
import com.kevinserrano.moviesnow.model.Movie;
import com.kevinserrano.moviesnow.utilities.Constants;
import com.kevinserrano.moviesnow.utilities.SystemUtils;
import com.kevinserrano.moviesnow.view.activities.MovieDetailsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Created by kevin
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {


    private LayoutInflater ensamblador;

    // An Activity's Context.
    private final Activity mActivity;

    // The list of movies.
    private List<Movie> mMovies = new ArrayList<>();

    private int mTypeView;

    public MovieAdapter(final Activity mActivity, int typeView) {
        this.mActivity = mActivity;
        this.mTypeView = typeView;
        this.ensamblador = LayoutInflater.from(this.mActivity);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

         TextView txtTitle;
         ImageView img;
         View itemView;
         MovieViewHolder(View itemView) {
            super(itemView);
             if(mTypeView == Constants.TYPE_VIEW_MOVIES)
                 txtTitle = itemView.findViewById(R.id.txtTitle);
            img = itemView.findViewById(R.id.img);
            this.itemView = itemView;
         }

    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = ensamblador.inflate(R.layout.item_movie, parent, false);
        if(mTypeView == Constants.TYPE_VIEW_RECEPT_MOVIES)
            itemView = ensamblador.inflate(R.layout.item_movie_favorite, parent, false);
        return new MovieViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, final int position) {

        final Movie movie = mMovies.get(
                holder.getAdapterPosition());

        String fileSize = "w400";//Tamaña grande

        if(holder.txtTitle!=null) {
            fileSize = "w200";//Tamaña pequeño
            holder.txtTitle.setText(movie.getOriginalTitle());
        }
        Glide.with(mActivity)
                .load(SystemUtils.getInstance().imagePathBuilder(fileSize,
                        movie.getPosterPath()))
                .apply(RequestOptions.placeholderOf(R.drawable.art_2))
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(34)))
                .into(holder.img);

        ViewCompat.setTransitionName(holder.img, movie.getOriginalTitle());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(mActivity, MovieDetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("movie", movie);
            intent.putExtras(bundle);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    mActivity,
                    holder.img,
                    Objects.requireNonNull(ViewCompat.getTransitionName(holder.img)));
            mActivity.startActivity(intent, options.toBundle());
        });

    }


   @Override
    public int getItemCount() {
        return mMovies.size();
    }


    public void addMovies(List<Movie> mMovies){
        this.mMovies = mMovies;
        notifyDataSetChanged();
    }


    public void crearAll(){
        mMovies.clear();
        notifyDataSetChanged();
    }


}
