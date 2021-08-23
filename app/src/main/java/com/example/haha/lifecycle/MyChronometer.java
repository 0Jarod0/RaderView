package com.example.haha.lifecycle;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Chronometer;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class MyChronometer extends Chronometer implements LifecycleObserver {
    private final String TAG = MyChronometer.class.getSimpleName();

    private long time = 1;
    public MyChronometer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void startMeter(){
        Log.i(TAG, "startMeter: onResume");
        setBase(SystemClock.elapsedRealtime()-time);
        start();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void stopMeter(){
        Log.i(TAG, "startMeter: onPause");
        time = SystemClock.elapsedRealtime() - getBase();
        stop();
    }
}
