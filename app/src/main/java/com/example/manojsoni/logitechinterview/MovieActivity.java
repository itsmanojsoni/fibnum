package com.example.manojsoni.logitechinterview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.manojsoni.logitechinterview.movie.database.MovieDatabase;
import com.example.manojsoni.logitechinterview.movie.movielist.MovieListFragment;

public class MovieActivity extends AppCompatActivity implements MovieListFragment.OnNextClicked {

    private static final String TAG = MovieActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_list_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MovieListFragment.newInstance())
                    .commitNow();
        }
    }

    @Override
    public void onNextClicked() {
        Log.d(TAG, "on Next Clicked. now display movies in database");

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, MovieDatabase.newInstance())
                .commitNow();

    }
}
