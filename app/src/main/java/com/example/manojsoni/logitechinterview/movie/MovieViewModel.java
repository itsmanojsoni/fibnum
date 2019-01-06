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
import io.reactivex.internal.operators.observable.ObservableFromCallable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class MovieViewModel extends ViewModel {

    private static final String TAG = MovieViewModel.class.getSimpleName();

    private MutableLiveData<List<Movie>> movieListLiveData = new MutableLiveData<>();


    private MutableLiveData<List<Movie>> movieListDatabaseLiveData = new MutableLiveData<>();



    public LiveData<List<Movie>> getMovieListLiveData() {
        return movieListLiveData;
    }

    public LiveData<List<Movie>> getMovieListDatabaseLiveData() {
        return movieListDatabaseLiveData;
    }

    LocalMovieDataSource localMovieDataSource;

    public void setLocalMovieDataSource(LocalMovieDataSource localMovieDataSource) {
        this.localMovieDataSource = localMovieDataSource;
    }


    public void loadMovieList() {
        MovieRepository.getInstance().getMovieList().subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Movie>>() {
                    @Override
                    public void onCompleted() {
                    }

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
                        movieListDatabaseLiveData.postValue(movies);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void insertOrUpdateMovie(Movie movie) {
        if (localMovieDataSource != null) {
            Observable.fromCallable(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    localMovieDataSource.insertOrUpdateMovie(movie);
                    return 0;
                }
            }).subscribeOn(io.reactivex.schedulers.Schedulers.io())
                    .subscribe();
        }
    }

    public void deleteMovie(Movie movie) {
        if (localMovieDataSource != null) {
            Observable.fromCallable(() -> {
                localMovieDataSource.deleteMovie(movie);
                return 0;
            }).subscribeOn(io.reactivex.schedulers.Schedulers.io())
                    .subscribe();
        }
    }

    public void deleteAllMovies() {
        if (localMovieDataSource != null) {
            Observable.fromCallable(() -> {
                localMovieDataSource.deleteAllMovies();
                return 0;
            }).subscribeOn(io.reactivex.schedulers.Schedulers.io())
                    .subscribe();
        }
    }
}


