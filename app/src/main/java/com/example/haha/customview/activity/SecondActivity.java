package com.example.haha.customview.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.haha.customview.R;
import com.example.haha.customview.manager.UserManager;

/**
 * Created by haha on 2020/3/1.
 */

public class SecondActivity  extends AppCompatActivity {
    private static String TAG = SecondActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.e(TAG, String.valueOf(UserManager.sUserId));
    }
}
