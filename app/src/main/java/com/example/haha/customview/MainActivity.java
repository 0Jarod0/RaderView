package com.example.haha.customview;


import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.haha.customview.activity.AutoBannerActivity;
import com.example.haha.customview.activity.BezierActivity;
import com.example.haha.customview.activity.CardViewActivity;
import com.example.haha.customview.activity.DownloadActivity;
import com.example.haha.customview.activity.FirstActivity;
import com.example.haha.customview.activity.FlexibleLayoutActivity;
import com.example.haha.customview.activity.LinkageActivity;
import com.example.haha.customview.activity.PasswordActivity;
import com.example.haha.customview.activity.RadarViewActivity;
import com.example.haha.customview.activity.RecyclerviewActivity;
import com.example.haha.customview.activity.SecondActivity;
import com.example.haha.customview.activity.ThreadTest;
import com.example.haha.customview.activity.WindowActivity;
import com.example.haha.customview.activity.WrapLinearLayoutActivity;
import com.example.haha.customview.adapter.MainAdapter;
import com.example.haha.customview.manager.UserManager;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;


public class MainActivity extends AppCompatActivity {

    private String[] data = {"WrapLinearLayout","AutoBanner","Bezier","Radar","Flexible","Download"
            ,"FirstActivity","SecondActivity","Window","CardView","ThreadLocal","二级联动","RecyclerView"
            ,"Password"};
    private List<String> list = new ArrayList<>();
    private MainAdapter mAdapter;

    private ListView mList;
    private Intent mIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserManager.sUserId = 2;
       initView();
    }
    public void initView(){
        mList = findViewById(R.id.listview);
        for (int i=0;i<data.length;i++){
            list.add(data[i]);
        }
        mAdapter = new MainAdapter(this,list);
        mList.setAdapter(mAdapter);

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        mIntent = new Intent(MainActivity.this, WrapLinearLayoutActivity.class);
                        startActivity(mIntent);
                        break;
                    case 1:
                        mIntent = new Intent(MainActivity.this, AutoBannerActivity.class);
                        startActivity(mIntent);
                        break;
                    case 2:
                        mIntent = new Intent(MainActivity.this, BezierActivity.class);
                        startActivity(mIntent);
                        break;
                    case 3:
                        mIntent = new Intent(MainActivity.this, RadarViewActivity.class);
                        startActivity(mIntent);
                        break;
                    case 4:
                        mIntent = new Intent(MainActivity.this, FlexibleLayoutActivity.class);
                        startActivity(mIntent);
                        break;
                    case 5:
                        mIntent = new Intent(MainActivity.this, DownloadActivity.class);
                        startActivity(mIntent);
                        break;
                    case 6:
                        mIntent = new Intent(MainActivity.this, FirstActivity.class);
                        startActivity(mIntent);
                        break;
                    case 7:
                        mIntent = new Intent(MainActivity.this, SecondActivity.class);
                        startActivity(mIntent);
                        break;
                    case 8:
                        mIntent = new Intent(MainActivity.this, WindowActivity.class);
                        startActivity(mIntent);
                        break;
                    case 9:
                        mIntent = new Intent(MainActivity.this, CardViewActivity.class);
                        startActivity(mIntent);
                        break;
                    case 10:
                        mIntent = new Intent(MainActivity.this, ThreadTest.class);
                        startActivity(mIntent);
                        break;
                    case 11:
                        mIntent = new Intent(MainActivity.this, LinkageActivity.class);
                        startActivity(mIntent);
                        break;
                    case 12:
                        mIntent = new Intent(MainActivity.this, RecyclerviewActivity.class);
                        startActivity(mIntent);
                        break;
                    case 13:
                        mIntent = new Intent(MainActivity.this, PasswordActivity.class);
                        startActivity(mIntent);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (Build.VERSION.SDK_INT >= 23 && Settings.canDrawOverlays(this)) {
                mIntent = new Intent(MainActivity.this, WindowActivity.class);
                startActivity(mIntent);
            }
        }
    }
}
