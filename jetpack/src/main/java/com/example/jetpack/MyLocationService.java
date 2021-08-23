package com.example.jetpack;

import android.util.Log;

import androidx.lifecycle.LifecycleService;

public class MyLocationService extends LifecycleService {
    private final String TAG = MyLocationService.class.getSimpleName();

    public MyLocationService() {
        Log.d(TAG, "MyLocationService: ");
        MyLocationObserver observer = new MyLocationObserver(this);
        getLifecycle().addObserver(observer);
    }
}
