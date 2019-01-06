package com.example.manojsoni.logitechinterview.movie.database;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.manojsoni.logitechinterview.R;
import com.example.manojsoni.logitechinterview.database.LocalMovieDataSource;
import com.example.manojsoni.logitechinterview.model.Movie;
import com.example.manojsoni.logitechinterview.movie.movielist.MovieListFragment;

import java.util.ArrayList;
import java.util.List;

public class MovieDatabaseFragment extends Fragment implements MovieDatabaseAdapter.OnItemClicked {

    public static MovieDatabaseFragment newInstance() {
        return new MovieDatabaseFragment();
    }

    private MovieDatabaseAdapter movieDatabaseAdapter;
    private MovieDatabaseFragment.OnMovieUpdateCallback callback;

    private List<Movie> movieList = new ArrayList<>();

    public interface OnMovieUpdateCallback {
        void deleteMovie(Movie movie);

        void deleteAllMovies();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movie_database_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (OnMovieUpdateCallback) context;
        } catch (ClassCastException e) {

            throw new ClassCastException(getActivity().toString()
                    + " must implement OnMovieUpdateCallback");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = getActivity().findViewById(R.id.movieDatabaseList);

        movieDatabaseAdapter = new MovieDatabaseAdapter(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(movieDatabaseAdapter);

        Button deleteAll = getActivity().findViewById(R.id.deleteAllBtn);

        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.deleteAllMovies();
            }
        });
    }

    public void setMovieList (List<Movie> movies) {
        this.movieList = movies;
        if (movieDatabaseAdapter != null) {
            movieDatabaseAdapter.setMovieList(movies);
            movieDatabaseAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClicked(int position) {
        if (callback != null) {
            callback.deleteMovie(movieList.get(position));
        }
    }
}
