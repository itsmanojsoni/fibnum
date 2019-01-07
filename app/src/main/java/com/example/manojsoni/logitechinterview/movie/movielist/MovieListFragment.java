package com.example.manojsoni.logitechinterview.movie.movielist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.manojsoni.logitechinterview.R;
import com.example.manojsoni.logitechinterview.model.Movie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Shows list of movies from the network
 */
public class MovieListFragment extends Fragment implements MovieListAdapter.OnItemClicked {

    private MovieListAdapter movieListAdapter;
    private List<Movie> movieList = new ArrayList<>();
    private OnActivityCallback callback;

    private static final int NUMBER_PER_PAGE = 3;

    private int startIndex = 0;
    private int endIndex = 0;


    private static final String TAG = MovieListFragment.class.getSimpleName();

    public interface OnActivityCallback {
        void onNextClicked();

        void insertOrUpdateMovie(Movie movie);
    }

    public static MovieListFragment newInstance() {
        return new MovieListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (OnActivityCallback) context;
        } catch (ClassCastException e) {

            throw new ClassCastException(getActivity().toString()
                    + " must implement OnMovieUpdateCallback");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movie_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button nextBtn = view.findViewById(R.id.nextBtn);
        Button deleteAllBtn = view.findViewById(R.id.DeleteMovieListAllBtn);
        Button sortBtn = view.findViewById(R.id.sortBtn);
        Button retrieveBtn = view.findViewById(R.id.retrieveBtn);

        initUi(view);

        nextBtn.setOnClickListener(view1 -> {
            if (callback != null) {
                callback.onNextClicked();
            }
        });

        deleteAllBtn.setOnClickListener(view12 -> {
            if (movieListAdapter != null) {
                startIndex = 0;
                endIndex = 0;
                movieListAdapter.setMovieList(new ArrayList<>());
            }
        });

        sortBtn.setOnClickListener(view13 -> {
            if (movieList != null && movieList.size() > 0) {
                startIndex = 0;
                endIndex = NUMBER_PER_PAGE;
                Collections.reverse(movieList);
                MovieListFragment.this.updateAdapterList();
            }
        });

        retrieveBtn.setOnClickListener(view14 -> {
            if (movieList != null && movieList.size() > 0) {
                if (startIndex + NUMBER_PER_PAGE < movieList.size()) {
                    startIndex = endIndex;
                    endIndex = startIndex + NUMBER_PER_PAGE;
                    MovieListFragment.this.updateAdapterList();
                }
            }
        });
    }

    public void initUi(@NonNull View view) {
        RecyclerView movieRv = view.findViewById(R.id.movieListRv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        movieRv.setLayoutManager(layoutManager);

        movieListAdapter = new MovieListAdapter(this);
        movieRv.setAdapter(movieListAdapter);
    }

    public void setMovieListFragment(@NonNull List<Movie> movies) {
        movieList.clear();
        movieList.addAll(movies);
        startIndex = 0;
        endIndex = NUMBER_PER_PAGE;
        updateAdapterList();

    }

    private void updateAdapterList() {
        if (movieListAdapter != null) {
            if (startIndex < movieList.size() && endIndex <= movieList.size()) {
                movieListAdapter.setMovieList(movieList.subList(startIndex, endIndex));
            }
        }
    }

    @Override
    public void onItemClicked(int position) {
        if (callback != null) {
            Log.d(TAG, "Insert Movie into database");
            callback.insertOrUpdateMovie(movieList.get(position));
        }
    }
}
