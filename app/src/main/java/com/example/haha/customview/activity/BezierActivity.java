package com.example.haha.customview.activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.haha.customview.R;
import com.example.haha.customview.view.Bezier;

/**
 * Created by haha on 2019/6/24.
 */

public class BezierActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier);
        //贝塞尔曲线
        Bezier bezier = new Bezier(this);
        LinearLayout ll = findViewById(R.id.ll);
        ll.addView(bezier);
    }
}
