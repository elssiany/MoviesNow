package com.kevinserrano.moviesnow.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class MovieTrailerPageResult implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("results")
    private ArrayList<MovieTrailer> trailerResult;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<MovieTrailer> getTrailerResult() {
        return trailerResult;
    }

    public void setTrailerResult(ArrayList<MovieTrailer> trailerResult) {
        this.trailerResult = trailerResult;
    }

}
