package com.example.haha.customview.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by haha on 2020/4/1.
 */

public class ThreadTest extends AppCompatActivity {

    private static final String TAG = "ThreadTest";
    //定义ThreadLoacl对象
    private ThreadLocal<Boolean> mBooleanThreadLocal = new ThreadLocal<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBooleanThreadLocal.set(true);
        Log.d(TAG,"[Thread#main]mBooleanThreadLocal="+mBooleanThreadLocal.get());

        new Thread("Thread#1"){
            @Override
            public void run() {
                mBooleanThreadLocal.set(false);
                Log.d(TAG,"[Thread#1]mBooleanThreadLocal="+mBooleanThreadLocal.get());
            }
        }.start();

        new Thread("Thread#2"){
            @Override
            public void run() {
                Log.d(TAG,"[Thread#2]mBooleanThreadLocal="+mBooleanThreadLocal.get());
            }
        }.start();
    }
}
