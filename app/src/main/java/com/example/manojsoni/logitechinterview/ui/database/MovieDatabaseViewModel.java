package com.example.manojsoni.logitechinterview.ui.database;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.manojsoni.logitechinterview.database.LocalMovieDataSource;
import com.example.manojsoni.logitechinterview.model.Movie;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MovieDatabaseViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private static final String TAG = MovieDatabaseViewModel.class.getSimpleName();

    public void loadMoviesDatabase(LocalMovieDataSource localMovieDataSource) {
        Observable.fromCallable(new Callable<List<Movie>>() {
            @Override
            public List<Movie> call() throws Exception {
               return localMovieDataSource.getAllMovies();

            }
        }).subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Movie>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Movie> movies) {

                        for (int i =0; i < movies.size(); i++) {
                            Log.d(TAG, "Movie Title here is  = "+movies.get(i).getTitle());

                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
