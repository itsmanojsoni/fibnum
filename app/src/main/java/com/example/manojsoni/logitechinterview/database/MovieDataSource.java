package com.example.manojsoni.logitechinterview.database;

import com.example.manojsoni.logitechinterview.model.Movie;

public interface MovieDataSource {

    void insertOrUpdateMovie(Movie movie);

    /**
     * Deletes all users from the data source.
     */
    void deleteAllMovies();
}
