package com.example.manojsoni.logitechinterview.ui.movielist;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.manojsoni.logitechinterview.R;
import com.example.manojsoni.logitechinterview.model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

public class MovieListFragment extends Fragment {

    private MovieListViewModel mViewModel;

    private RecyclerView movieRv;
    private MovieListAdapter movieListAdapter;
    private List<Movie> movieList = new ArrayList<>();

    public static MovieListFragment newInstance() {
        return new MovieListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movie_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        movieRv = getActivity().findViewById(R.id.movieListRv);

        mViewModel = ViewModelProviders.of(this).get(MovieListViewModel.class);
        // TODO: Use the ViewModel
        mViewModel.getMovieListLiveData().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {

                movieList.clear();
                movieList.addAll(movies);
                movieListAdapter.setMovieList(movieList);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        movieRv.setLayoutManager(layoutManager);

        movieListAdapter = new MovieListAdapter();
        movieRv.setAdapter(movieListAdapter);

        mViewModel.loadMovieList();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }
}
