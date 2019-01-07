package com.example.manojsoni.logitechinterview.movie;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.manojsoni.logitechinterview.R;
import com.example.manojsoni.logitechinterview.database.LocalMovieDataSource;
import com.example.manojsoni.logitechinterview.model.Movie;
import com.example.manojsoni.logitechinterview.movie.database.MovieDatabaseFragment;
import com.example.manojsoni.logitechinterview.movie.movielist.MovieListFragment;

/**
 * Movie activity to handler movie related things
 */

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
        Log.d(TAG, "subscribe to view model");
        viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        viewModel.getMovieListLiveData().observe(this, movies -> {
            if (movies != null && movies.size() > 0) {
                movieListFragment.setMovieListFragment(movies);
            }
        });

        viewModel.getMovieListDatabaseLiveData().observe(this, movies -> movieDatabaseFragment.setMovieList(movies));

        viewModel.setLocalMovieDataSource(LocalMovieDataSource.getInstance(this));
        viewModel.loadMovieFromNetwork();
    }

    @Override
    public void onNextClicked() {
        movieDatabaseFragment =    MovieDatabaseFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, movieDatabaseFragment)
                .addToBackStack(null)
                .commit();

        if (viewModel != null) {
            viewModel.loadMoviesFromDatabase(LocalMovieDataSource.getInstance(this));
        }
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
