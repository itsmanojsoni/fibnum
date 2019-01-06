package com.example.manojsoni.logitechinterview.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.manojsoni.logitechinterview.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {


    @Query("SELECT * FROM movies")
    List<Movie> getMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movie);

    @Query("DELETE FROM movies")
    void deleteAllMovies();


}
