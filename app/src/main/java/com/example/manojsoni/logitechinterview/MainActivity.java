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
import android.widget.RadioGroup;

import com.example.manojsoni.logitechinterview.fibNum.FibNumAdapter;
import com.example.manojsoni.logitechinterview.fibNum.FibNumViewModel;
import com.example.manojsoni.logitechinterview.movie.MovieActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int MAX_FIB_NUM_INDEX = 70;
    private static final int NUMBER_PER_PAGE = 10;

    private FibNumAdapter fibNumAdapter;
    private FibNumViewModel fibNumViewModel;

    private final List<String> fibNumberList = new ArrayList<>();
    private int startIndex = 0;
    private int endIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button nextBtn = findViewById(R.id.nextBtn);
        Button clearBtn = findViewById(R.id.clearBtn);
        Button resetBtn = findViewById(R.id.resetBtn);
        Button retrieveBtn = findViewById(R.id.retrieveBtn);

        initUi();

        subsribeToViewModel();

        nextBtn.setOnClickListener(view -> {
            Intent myIntent = new Intent(MainActivity.this, MovieActivity.class);
            startActivity(myIntent);
        });

        clearBtn.setOnClickListener(view -> {
            startIndex = 0;
            endIndex = 0;
            updateUi();
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startIndex = 0;
                endIndex = NUMBER_PER_PAGE;
                updateUi();

            }
        });

        retrieveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (startIndex + NUMBER_PER_PAGE < fibNumberList.size()) {
                    startIndex = endIndex;
                    endIndex = startIndex + NUMBER_PER_PAGE;
                    updateUi();
                }
            }
        });
    }

    private void initUi() {
        RecyclerView fibNumRv = findViewById(R.id.fibNumListRv);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        fibNumRv.setLayoutManager(layoutManager);
        fibNumAdapter = new FibNumAdapter();
        fibNumRv.setAdapter(fibNumAdapter);
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
                fibNumberList.clear();
                fibNumberList.addAll(fibNumList);
                startIndex = 0;
                endIndex = NUMBER_PER_PAGE;
                updateUi();
            }
        });

        fibNumViewModel.getFibNumberList(FibNumViewModel.DATASOURCE.JAVA, MAX_FIB_NUM_INDEX);
    }

    private void updateUi() {
        if (startIndex < fibNumberList.size() && endIndex <= fibNumberList.size()) {
            fibNumAdapter.setData(startIndex, fibNumberList.subList(startIndex, endIndex));
        }
    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.Java:
                if (checked)
                    // Pirates are the best
                        startIndex = 0;
                        endIndex = 0;
                        fibNumViewModel.getFibNumberList(FibNumViewModel.DATASOURCE.JAVA, MAX_FIB_NUM_INDEX);

                break;
            case R.id.JNI:
                if (checked) {
                        startIndex = 0;
                        endIndex = 0;
                        fibNumViewModel.getFibNumberList(FibNumViewModel.DATASOURCE.JNI, MAX_FIB_NUM_INDEX);
                }
                break;
        }
    }
}
