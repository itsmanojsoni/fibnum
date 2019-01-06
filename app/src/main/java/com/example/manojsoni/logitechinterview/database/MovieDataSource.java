package com.example.manojsoni.logitechinterview.database;

import com.example.manojsoni.logitechinterview.model.Movie;

import java.util.List;

public interface MovieDataSource {

    void insertOrUpdateMovie(Movie movie);


    void deleteAllMovies();

    List<Movie> getAllMovies();
}
