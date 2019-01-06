package com.example.manojsoni.logitechinterview.fibNum;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FibNumViewModel extends ViewModel {

    public enum DATASOURCE {
        JAVA,
        JNI
    }

    private static final String TAG = FibNumViewModel.class.getSimpleName();

    private MutableLiveData<List<String>> fibNumList = new MutableLiveData<>();


    public LiveData<List<String>> getFibNumberList() {
        return fibNumList;
    }


    public void getFibNumberList(DATASOURCE datasource, int number) {
        Observable<List<String>> observable = null;


        if (datasource == DATASOURCE.JAVA) {
            Log.d(TAG, "get Fib Number From JAVA");
            observable = FibNumDataSource.getFibNumFromJava(number);
        } else if (datasource == DATASOURCE.JNI) {
            Log.d(TAG, "get Fib Number From JNI");
            observable = FibNumDataSource.getFibNumerFromJNI(number);
        }

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<String> numbers) {
                        Log.d(TAG, "got the number and it is = " + numbers.size());
                        fibNumList.postValue(numbers);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}

