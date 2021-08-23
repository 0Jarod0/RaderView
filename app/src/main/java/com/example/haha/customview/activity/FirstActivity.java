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

public class FirstActivity extends AppCompatActivity {
    private static String TAG = FirstActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Log.e(TAG, String.valueOf(UserManager.sUserId));


    }
}
