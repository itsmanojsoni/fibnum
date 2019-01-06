package com.example.manojsoni.logitechinterview.movie;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.manojsoni.logitechinterview.R;
import com.example.manojsoni.logitechinterview.database.LocalMovieDataSource;
import com.example.manojsoni.logitechinterview.model.Movie;
import com.example.manojsoni.logitechinterview.movie.database.MovieDatabaseFragment;
import com.example.manojsoni.logitechinterview.movie.movielist.MovieListFragment;

import java.util.List;

public class MovieActivity extends AppCompatActivity implements MovieListFragment.OnNextClicked {

    private static final String TAG = MovieActivity.class.getSimpleName();

    private MovieListFragment movieListFragment;
    private MovieViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_list_activity);
        if (savedInstanceState == null) {
            movieListFragment= MovieListFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, movieListFragment)
                    .commitNow();
        }
        subscribeToViewModel();
    }

    private void subscribeToViewModel() {

        viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        // TODO: Use the ViewModel
        viewModel.getMovieListLiveData().observe(this, new Observer<List<Movie>>() {

            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                movieListFragment.setMovieListAdapter(movies);
            }
        });

        viewModel.loadMovieList();
    }

    @Override
    public void onNextClicked() {
        Log.d(TAG, "on Next Clicked. now display movies in database");
        MovieDatabaseFragment movieDatabaseFragment =    MovieDatabaseFragment.newInstance();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, movieDatabaseFragment)
                .commitNow();

        viewModel.loadMoviesDatabase(LocalMovieDataSource.getInstance(this));

    }
}
