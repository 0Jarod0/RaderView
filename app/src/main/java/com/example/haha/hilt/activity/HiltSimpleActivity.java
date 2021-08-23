package com.example.haha.hilt.activity;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.haha.customview.R;
import com.example.haha.hilt.fragment.HiltFragment;

import dagger.hilt.android.AndroidEntryPoint;

public class HiltSimpleActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hilt_simple);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, HiltFragment.class,null)
                .commit();
    }
}
