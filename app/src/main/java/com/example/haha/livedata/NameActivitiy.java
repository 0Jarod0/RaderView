package com.example.haha.livedata;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.haha.customview.R;

public class NameActivitiy extends AppCompatActivity {

    private NameViewModel model;
    private TextView tvTxt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dragger);
        tvTxt = findViewById(R.id.tv_txt);
        model = new ViewModelProvider(this).get(NameViewModel.class);

        final Observer<String> nameObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvTxt.setText(s);
            }
        };
        model.getCurrentName().observe(this,nameObserver);

        findViewById(R.id.b_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = "测试";
                model.getCurrentName().setValue(name);
            }
        });
    }
}
