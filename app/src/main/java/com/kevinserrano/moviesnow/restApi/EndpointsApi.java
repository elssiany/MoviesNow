package com.kevinserrano.moviesnow.restApi;


import com.kevinserrano.moviesnow.model.MoviePageResult;
import com.kevinserrano.moviesnow.model.MovieTrailerPageResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface EndpointsApi {


    @GET("movie/upcoming")
    Call<MoviePageResult> getUpcomingMovies(@Query("page") int page, @Query("api_key") String userkey);

    @GET("movie/popular")
    Call<MoviePageResult> getPopularMovies(@Query("page") int page, @Query("api_key") String userkey);

    @GET("movie/top_rated")
    Call<MoviePageResult> getTopRatedMovies(@Query("page") int page, @Query("api_key") String userkey);

    @GET("movie/{id}/videos")
    Call<MovieTrailerPageResult> getTrailers(@Path("id") int movieId, @Query("api_key") String userkey);

}
