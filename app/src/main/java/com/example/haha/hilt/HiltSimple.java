package com.example.haha.hilt;

import android.util.Log;

import javax.inject.Inject;

public class HiltSimple {
    private final String TAG = HiltSimple.class.getSimpleName();
    @Inject
    public HiltSimple() {
    }

    public void doSomething(){
        Log.i(TAG, "doSomething: ");
    }
}
