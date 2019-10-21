package com.kevinserrano.moviesnow.view.adapter;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.kevinserrano.moviesnow.R;
import com.kevinserrano.moviesnow.model.Movie;
import com.kevinserrano.moviesnow.model.MovieTrailer;
import com.kevinserrano.moviesnow.utilities.SystemUtils;
import com.kevinserrano.moviesnow.view.activities.MovieDetailsActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by kevin
 */
public class MovieSearchResultAdapter extends RecyclerView.Adapter<MovieSearchResultAdapter.MovieSearchResultViewHolder> {

    private LayoutInflater ensamblador;

    // An Activity's Context.
    private final Activity mActivity;

    // The list of movies.
    private List<Movie> mMovies = new ArrayList<>();

    public MovieSearchResultAdapter(final Activity mActivity) {
        this.mActivity = mActivity;
        this.ensamblador = LayoutInflater.from(this.mActivity);
    }

    class MovieSearchResultViewHolder extends RecyclerView.ViewHolder {

         TextView txtTitle;
         ImageView img;
         View itemView;
        MovieSearchResultViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            img = itemView.findViewById(R.id.img);
            this.itemView = itemView;
         }

    }

    @NonNull
    @Override
    public MovieSearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieSearchResultViewHolder(ensamblador.inflate(R.layout.item_movie_searr,
                parent, false));
    }



    @Override
    public void onBindViewHolder(@NonNull final MovieSearchResultViewHolder holder, final int position) {

        final Movie movie = mMovies.get(
                holder.getAdapterPosition());

        holder.txtTitle.setText(movie.getOriginalTitle());

        Glide.with(mActivity)
                .load(SystemUtils.getInstance().imagePathBuilder("w200",
                        movie.getPosterPath()))
                .apply(RequestOptions.placeholderOf(R.drawable.art_2))
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(34)))
                .into(holder.img);

        holder.itemView.setOnClickListener(view -> {
            if(SystemUtils.getInstance().isNetworkAvailable(mActivity)){
                Intent intent = new Intent(mActivity, MovieDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movie", movie);
                intent.putExtras(bundle);
                mActivity.startActivity(intent);
            } else {
                Toast.makeText(mActivity, R.string.message_error_1,
                        Toast.LENGTH_SHORT).show();
            }
        });

    }


   @Override
    public int getItemCount() {
        return mMovies.size();
    }


    public void addMovie(Movie mMovie){
        mMovies.add(mMovie);
        notifyItemInserted(mMovies.size());
    }


    public void crearAll(){
        mMovies.clear();
        notifyDataSetChanged();
    }

}
