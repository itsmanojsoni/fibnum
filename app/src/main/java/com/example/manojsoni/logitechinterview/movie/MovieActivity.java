package com.example.manojsoni.logitechinterview.movie;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.manojsoni.logitechinterview.R;
import com.example.manojsoni.logitechinterview.database.LocalMovieDataSource;
import com.example.manojsoni.logitechinterview.model.Movie;
import com.example.manojsoni.logitechinterview.movie.database.MovieDatabaseFragment;
import com.example.manojsoni.logitechinterview.movie.movielist.MovieListFragment;

import java.util.Collections;
import java.util.List;

public class MovieActivity extends AppCompatActivity implements
        MovieListFragment.OnActivityCallback, MovieDatabaseFragment.OnMovieUpdateCallback {

    private static final String TAG = MovieActivity.class.getSimpleName();

    private MovieListFragment movieListFragment;
    private MovieDatabaseFragment movieDatabaseFragment;
    private MovieViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_list_activity);
        if (savedInstanceState == null) {
            movieListFragment= MovieListFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, movieListFragment)
                    .addToBackStack(null)
                    .commit();
        }
        subscribeToViewModel();
    }

    private void subscribeToViewModel() {

        viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        // TODO: Use the ViewModel
        viewModel.getMovieListLiveData().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                if (movies != null && movies.size() > 0) {
                    movieListFragment.setMovieListFragment(movies);
                }
            }
        });

        viewModel.getMovieListDatabaseLiveData().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                movieDatabaseFragment.setMovieList(movies);
            }
        });

        viewModel.setLocalMovieDataSource(LocalMovieDataSource.getInstance(this));
        viewModel.loadMovieList();
    }

    @Override
    public void onNextClicked() {
        Log.d(TAG, "on Next Clicked. now display movies in database");
        movieDatabaseFragment =    MovieDatabaseFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, movieDatabaseFragment)
                .addToBackStack(null)
                .commit();

        viewModel.loadMoviesDatabase(LocalMovieDataSource.getInstance(this));

    }

    @Override
    public void insertOrUpdateMovie(Movie movie) {
        if (viewModel != null) {
            Toast.makeText(this, "Insert Movie to database",
                    Toast.LENGTH_SHORT).show();
            viewModel.insertOrUpdateMovie(movie);
        }
    }


    @Override
    public void deleteMovie(Movie movie) {
        if (viewModel != null) {
            Toast.makeText(this, "Delete  Movie",
                    Toast.LENGTH_SHORT).show();
            viewModel.deleteMovie(movie);
        }
    }

    @Override
    public void deleteAllMovies() {
        if (viewModel != null) {
            Toast.makeText(this, "Delete All Movies",
                    Toast.LENGTH_SHORT).show();
            viewModel.deleteAllMovies();
        }
    }
}
