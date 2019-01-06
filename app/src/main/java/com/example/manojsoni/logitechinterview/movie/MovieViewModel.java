package com.example.manojsoni.logitechinterview.movie;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.manojsoni.logitechinterview.database.LocalMovieDataSource;
import com.example.manojsoni.logitechinterview.model.Movie;
import com.example.manojsoni.logitechinterview.repository.MovieRepository;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class MovieViewModel extends ViewModel {

    private static final String TAG = MovieViewModel.class.getSimpleName();

    private MutableLiveData<List<Movie>> movieListLiveData = new MutableLiveData<>();

    public LiveData<List<Movie>> getMovieListLiveData() {
        return movieListLiveData;
    }


    public void loadMovieList() {

        MovieRepository.getInstance().getMovieList().subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Movie>>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error = " + e.getMessage());
                    }

                    @Override
                    public void onNext(List<Movie> movies) {

                        for (Movie movie : movies) {
                            Log.d(TAG, "Movie name is = " + movie.title);
                        }
                        movieListLiveData.postValue(movies);
                    }
                });
    }


    public void loadMoviesDatabase(LocalMovieDataSource localMovieDataSource) {
        Observable.fromCallable(new Callable<List<Movie>>() {
            @Override
            public List<Movie> call() throws Exception {
                return localMovieDataSource.getAllMovies();

            }
        }).subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .subscribe(new io.reactivex.Observer<List<Movie>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Movie> movies) {

                        for (int i = 0; i < movies.size(); i++) {
                            Log.d(TAG, "Movie Title here is  = " + movies.get(i).getTitle());

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


