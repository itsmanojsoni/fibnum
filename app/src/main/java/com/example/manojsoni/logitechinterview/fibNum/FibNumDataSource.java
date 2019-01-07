package com.example.manojsoni.logitechinterview.fibNum;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;

public class FibNumDataSource {

    static {
        System.loadLibrary("native-lib");
    }

    public static Observable<List<String>> getFibNumFromJava(final  int number) {
        return Observable.fromCallable(() -> getFibNumber(number));
    }

    private static List<String> getFibNumber(final  int number) {
        long first=0;
        long second=1;
        long next;
        int i;

        List<String> fiboNumList = new ArrayList<>();

        String info = " (" + "JAVA" + ")";

        for(i=0;i<number;i++)
        {
            if(i<=1)
                next = i;
            else
            {
                next = first + second;
                first = second;
                second = next;
            }

            fiboNumList.add(String.valueOf(next) + info);
        }

        return fiboNumList;
    }

    public static Observable<List<String>> getFibNumerFromJNI(final int number) {
        return Observable.fromCallable(new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                long[] output = getFibNum(number);
                String info = " (" + "JNI" + ")";
                List<String> result = new ArrayList<>();
                for (int i = 0; i < output.length; i++) {
                    result.add(String.valueOf(output[i]) + info);
                }
                return result;
            }
        });
    }

    public static native long[] getFibNum(int num);

}
