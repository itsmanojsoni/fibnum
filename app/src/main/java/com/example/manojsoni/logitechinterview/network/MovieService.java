package com.example.manojsoni.logitechinterview.network;

import com.example.manojsoni.logitechinterview.model.Movie;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

public interface MovieService {

    @GET("/fixture/movies.json")
    Observable<List<Movie>> getMovieList();
}
