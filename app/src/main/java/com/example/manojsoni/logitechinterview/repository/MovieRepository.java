package com.example.manojsoni.logitechinterview.repository;

import com.example.manojsoni.logitechinterview.model.Movie;
import com.example.manojsoni.logitechinterview.network.MovieService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class MovieRepository {

    private static MovieRepository sInstance;
    private MovieService movieService;

    private static final String MOVIES_BASE_URL = "http://eng-assets.s3-website-us-west-2.amazonaws.com";

    private MovieRepository() {


        final Gson gson =
                new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(MOVIES_BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        movieService = retrofit.create(MovieService.class);
    }

    public static MovieRepository getInstance() {

        if (sInstance == null) {
            sInstance =  new MovieRepository();
        }

        return sInstance;
    }

    public Observable<List<Movie>> getMovieList() {
        return movieService.getMovieList();
    }
}
