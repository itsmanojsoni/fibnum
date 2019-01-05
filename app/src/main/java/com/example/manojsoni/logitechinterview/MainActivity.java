package com.example.manojsoni.logitechinterview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    RecyclerView fibNumRv;

    private FibNumAdapter fibNumAdapter;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fibNumRv = findViewById(R.id.fibNumListRv);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        fibNumRv.setLayoutManager(layoutManager);
        fibNumAdapter = new FibNumAdapter();
        fibNumRv.setAdapter(fibNumAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Observable<List<String>> fibObservable =  callThisMethod();

        fibObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<String> strings) {

                        for (String s : strings) {
                            Log.d(TAG, "Fib Number is "+s);
                        }

                        fibNumAdapter.setData(strings);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private Observable<List<String>>  callThisMethod() {

        return Observable.fromCallable(new Callable<List<String>>() {

            @Override
            public List<String> call() throws Exception {

                long[] output = getFibNum(10);

                List<String> result = new ArrayList<>();
                for (int i = 0; i < output.length; i++) {
                    result.add(String.valueOf(output[i]));
                }

                return result;

            }
        });
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    public native long[] getFibNum (int num);

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.Java:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.JNI:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }
}
