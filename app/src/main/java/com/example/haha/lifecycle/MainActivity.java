package com.example.haha.lifecycle;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.haha.customview.R;

public class MainActivity extends AppCompatActivity {

    private MyChronometer chronometer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life);
        chronometer = findViewById(R.id.chronometer);
        getLifecycle().addObserver(chronometer);
    }

}