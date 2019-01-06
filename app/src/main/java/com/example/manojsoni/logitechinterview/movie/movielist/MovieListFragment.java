package com.example.manojsoni.logitechinterview.movie.movielist;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import com.example.manojsoni.logitechinterview.database.LocalMovieDataSource;
import com.example.manojsoni.logitechinterview.model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MovieListFragment extends Fragment implements MovieListAdapter.OnItemClicked {

    private MovieListViewModel mViewModel;

    private RecyclerView movieRv;
    private Button nextBtn;
    private MovieListAdapter movieListAdapter;
    private List<Movie> movieList = new ArrayList<>();
    private LocalMovieDataSource localMovieDataSource;

    private OnNextClicked onNextClicked;

    private static final String TAG = MovieListFragment.class.getSimpleName();

    public interface OnNextClicked {
        void onNextClicked();
    }


    public static MovieListFragment newInstance() {
        return new MovieListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            onNextClicked = (OnNextClicked) context;
        } catch (ClassCastException e) {

            throw new ClassCastException(getActivity().toString()
                    + " must implement OnNextClicked");
        }
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
        nextBtn = getActivity().findViewById(R.id.nextBtn);

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

        movieListAdapter = new MovieListAdapter(this);
        movieRv.setAdapter(movieListAdapter);

        mViewModel.loadMovieList();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "next button clicked");
                onNextClicked.onNextClicked();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        localMovieDataSource = LocalMovieDataSource.getInstance(getContext());
    }

    @Override
    public void onItemClicked(int position) {
        Observable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                localMovieDataSource.insertOrUpdateMovie(movieList.get(position));
                return 0;
            }
        }).subscribeOn(Schedulers.io())
                .subscribe();

    }
}
