package com.example.manojsoni.logitechinterview;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FibNumViewModel extends ViewModel {

    enum DATASOURCE {
        JAVA,
        JNI
    }

    private static final String TAG = FibNumViewModel.class.getSimpleName();

    private MutableLiveData<List<String>> fibNumList = new MutableLiveData<>();


    public LiveData<List<String>> getFibNumberList() {
        return fibNumList;
    }


    public void getFibNumberList(DATASOURCE datasource, int number) {

//        if (datasource == DATASOURCE.JAVA) {
//            observable = getFibNumerFromJAVA(number);
//        } else {
//            observable = getFibNumerFromJNI(number);
//        }


        Observable<List<String>> observable = FibNumDataSource.getFibNumberList(number);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<String> numbers) {
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

