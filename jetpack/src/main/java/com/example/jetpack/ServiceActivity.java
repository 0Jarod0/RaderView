package com.example.jetpack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ServiceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_two);
    }

    public void startGps(View view) {
        startService(new Intent(this,MyLocationService.class));
    }

    public void stopGps(View view) {
        stopService(new Intent(this,MyLocationService.class));
    }
}
