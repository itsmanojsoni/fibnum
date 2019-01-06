package com.example.manojsoni.logitechinterview.ui.movielist;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.manojsoni.logitechinterview.model.Movie;
import com.example.manojsoni.logitechinterview.repository.MovieRepository;

import java.util.List;

import rx.Observer;
import rx.schedulers.Schedulers;

public class MovieListViewModel extends ViewModel {


    private static final String TAG = MovieListViewModel.class.getSimpleName();
    // TODO: Implement the ViewModel


    public void loadMovieList() {

        MovieRepository.getInstance().getMovieList().subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Movie>>() {
                    @Override
                    public void onCompleted() {

                        Log.d(TAG, "on Completed");

                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e(TAG, "Error = "+e.getMessage());
                    }

                    @Override
                    public void onNext(List<Movie> movies) {

                        for (Movie movie : movies) {
                            Log.d(TAG, "Movie name is = "+movie.title);
                        }
                    }
                });
    }
}


