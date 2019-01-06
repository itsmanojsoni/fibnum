package com.example.manojsoni.logitechinterview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.manojsoni.logitechinterview.ui.movielist.MovieListFragment;

public class MovieListActivity extends AppCompatActivity {

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
}
