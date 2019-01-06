package com.example.manojsoni.logitechinterview;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.manojsoni.logitechinterview.fibNum.FibNumAdapter;
import com.example.manojsoni.logitechinterview.fibNum.FibNumViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView fibNumRv;

    private Button nextBtn;



    private FibNumAdapter fibNumAdapter;

    private static final int MAX_FIB_NUM_INDEX = 50;

    private FibNumViewModel fibNumViewModel;

    private List<String> fibNumberList = new ArrayList<>();

    private static final int NUMBER_PER_PAGE = 10;

    private int startIndex = 0;

    // Used to load the 'native-lib' library on application startup.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fibNumRv = findViewById(R.id.fibNumListRv);
        nextBtn = findViewById(R.id.nextBtn);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        fibNumRv.setLayoutManager(layoutManager);
        fibNumAdapter = new FibNumAdapter();
        fibNumRv.setAdapter(fibNumAdapter);

        subsribeToViewModel();

        nextBtn.setOnClickListener(view -> {
            Intent myIntent = new Intent(MainActivity.this, MovieActivity.class);
            startActivity(myIntent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void subsribeToViewModel() {
        fibNumViewModel = ViewModelProviders.of(this).get(FibNumViewModel.class);

        fibNumViewModel.getFibNumberList().observe(this, new android.arch.lifecycle.Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> fibNumList) {
                Log.d(TAG, "Fib Number List changed");
                fibNumberList.clear();
                fibNumberList.addAll(fibNumList);
                startIndex = 0;
                updateUi(startIndex , NUMBER_PER_PAGE);
            }
        });
    }

    private void updateUi(int start, int end) {
        fibNumAdapter.setData(fibNumberList.subList(start, end));
    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.Java:
                if (checked)
                    // Pirates are the best
                    if (fibNumViewModel != null) {
                        fibNumViewModel.getFibNumberList(FibNumViewModel.DATASOURCE.JAVA, MAX_FIB_NUM_INDEX);
                    }

                    break;
            case R.id.JNI:
                if (checked) {
                    if (fibNumViewModel != null) {
                        fibNumViewModel.getFibNumberList(FibNumViewModel.DATASOURCE.JNI, MAX_FIB_NUM_INDEX);
                    }
                }
                break;
        }
    }
}
