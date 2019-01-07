package com.example.manojsoni.logitechinterview.movie;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.manojsoni.logitechinterview.database.LocalMovieDataSource;
import com.example.manojsoni.logitechinterview.model.Movie;
import com.example.manojsoni.logitechinterview.repository.MovieRemoteDataSource;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

//import io.reactivex.Observable;
//import io.reactivex.disposables.Disposable;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class MovieViewModel extends ViewModel {

    private static final String TAG = MovieViewModel.class.getSimpleName();

    private MutableLiveData<List<Movie>> movieListLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Movie>> movieListDatabaseLiveData = new MutableLiveData<>();

    private LocalMovieDataSource localMovieDataSource;

    public LiveData<List<Movie>> getMovieListLiveData() {
        return movieListLiveData;
    }

    public LiveData<List<Movie>> getMovieListDatabaseLiveData() {
        return movieListDatabaseLiveData;
    }

    public void setLocalMovieDataSource(LocalMovieDataSource localMovieDataSource) {
        this.localMovieDataSource = localMovieDataSource;
    }

    public void loadMovieList() {
        MovieRemoteDataSource.getInstance().getMovieList()
                .map(movies -> {
                    Collections.sort(movies, (movie1, movie2) -> movie1.getTitle().compareToIgnoreCase(movie2.getTitle()));

                    return movies;
                }).subscribeOn(Schedulers.io())
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
                        if (movies != null && movies.size() > 0) {
                            movieListLiveData.postValue(movies);
                        }
                    }
                });
    }


    public void loadMoviesDatabase(LocalMovieDataSource localMovieDataSource) {
        Observable.fromCallable(() -> localMovieDataSource.getAllMovies()).subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Movie>>() {

                    @Override
                    public void onNext(List<Movie> movies) {
                        if (movies != null && movies.size() > 0) {
                            movieListDatabaseLiveData.postValue(movies);
                        }
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error getting movies from the database : " + e.getMessage());
                    }

                });
    }

    public void insertOrUpdateMovie(Movie movie) {
        if (localMovieDataSource != null) {
            Observable.fromCallable(() -> {
                localMovieDataSource.insertOrUpdateMovie(movie);
                return 0;
            }).subscribeOn(Schedulers.io())
                    .subscribe();
        }
    }

    public void deleteMovie(Movie movie) {
        if (localMovieDataSource != null) {
            Observable.fromCallable(() -> {
                localMovieDataSource.deleteMovie(movie);
                return 0;
            }).subscribeOn(Schedulers.io())
                    .subscribe();
        }
    }

    public void deleteAllMovies() {
        if (localMovieDataSource != null) {
            Observable.fromCallable(() -> {
                localMovieDataSource.deleteAllMovies();
                return 0;
            }).subscribeOn(Schedulers.io())
                    .subscribe();
        }
    }
}


