package com.kevinserrano.moviesnow.restApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitInstance {

    private static Retrofit mRetrofit;
    private static final String ROOT_URL = "http://api.themoviedb.org/3/";

    public static Retrofit getRetrofitInstance(){
        if(mRetrofit == null){
            mRetrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(ROOT_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }
}
