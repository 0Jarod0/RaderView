package com.example.haha.customview.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.haha.customview.R;
import com.example.haha.customview.view.WrapLinearLayout;

/**
 * Created by haha on 2019/6/24.
 * 从右至左横向排列标签
 */

public class WrapLinearLayoutActivity extends AppCompatActivity {

    private Context mContext;
    private WrapLinearLayout mWl;

    private String[] mTitles = new String[]{"android","androidstudio","androidView","自定义view","这是测试看看到底有多长"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrap_linear_layout);

        mContext = this;
        mWl = findViewById(R.id.wl);
        for (int i=0;i<mTitles.length;i++){
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_wrap, null,false);
            TextView title = view.findViewById(R.id.title);
            title.setText(mTitles[i]);
            mWl.addView(view);
        }
    }
}
