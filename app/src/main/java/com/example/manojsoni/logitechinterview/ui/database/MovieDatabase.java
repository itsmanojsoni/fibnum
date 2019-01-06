package com.example.manojsoni.logitechinterview.ui.database;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.manojsoni.logitechinterview.R;

public class MovieDatabase extends Fragment {

    private MovieDatabaseViewModel mViewModel;

    public static MovieDatabase newInstance() {
        return new MovieDatabase();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movie_database_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MovieDatabaseViewModel.class);
        // TODO: Use the ViewModel
    }

}
