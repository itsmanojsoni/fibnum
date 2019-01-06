package com.example.manojsoni.logitechinterview;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;

public class FibNumDataSource {

    private static final String TAG = FibNumDataSource.class.getSimpleName();

    static {
        System.loadLibrary("native-lib");
    }

    public static Observable<List<String>> getFibNumberList(int number) {

        return getFibNumerFromJNI(number);

    }

    private static Observable<List<String>> getFibNumerFromJNI(final int number) {
        return io.reactivex.Observable.fromCallable(new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                long[] output = getFibNum(number);
                List<String> result = new ArrayList<>();
                for (int i = 0; i < output.length; i++) {
                    Log.d(TAG, "output  is = "+output[i]);
                    result.add(String.valueOf(output[i]));
                }
                return result;
            }
        });
    }

    public static native long[] getFibNum(int num);

}
