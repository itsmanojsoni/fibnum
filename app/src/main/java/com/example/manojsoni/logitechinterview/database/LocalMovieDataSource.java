package com.example.manojsoni.logitechinterview.database;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.manojsoni.logitechinterview.model.Movie;

import java.util.List;

public class LocalMovieDataSource implements MovieDataSource {

    private static volatile LocalMovieDataSource INSTANCE;

    private MovieDao movieDao;


    LocalMovieDataSource(MovieDao dao) {
        this.movieDao = dao;
    }

    public static LocalMovieDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            synchronized (LocalMovieDataSource.class) {
                if (INSTANCE == null) {
                    MovieDatabase database = MovieDatabase.getInstance(context);
                    INSTANCE = new LocalMovieDataSource(database.movieDao());
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void insertOrUpdateMovie(Movie movie) {
        movieDao.insertMovie(movie);
    }

    @Override
    public void deleteAllMovies() {
        movieDao.deleteAllMovies();

    }

    @Override
    public void deleteMovie(Movie movie) {
        movieDao.deleteMovie(movie);
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieDao.getMovies();
    }
}
