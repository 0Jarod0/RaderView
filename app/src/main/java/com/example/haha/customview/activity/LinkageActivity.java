package com.example.haha.customview.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haha.customview.R;
import com.example.haha.customview.adapter.FirstAdapter;
import com.example.haha.customview.adapter.SecondAdapter;
import com.example.haha.customview.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haha on 2020/4/26.
 */

public class LinkageActivity extends AppCompatActivity {

    private RecyclerView mFirstRecycler,mSecondRecycler;
    private FirstAdapter mFirstAdapter;
    private SecondAdapter mSecondAdapter;
    private List<String> mFirstList = new ArrayList<>();
    private List<String> mSecondList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkage);

        mFirstRecycler = findViewById(R.id.first);
        mSecondRecycler = findViewById(R.id.second);
        mFirstList.add("新疆");
        mFirstList.add("四川");
        mFirstList.add("河南");
        mFirstList.add("甘肃");
        mFirstAdapter = new FirstAdapter(this,mFirstList);
        LinearLayoutManager manager = new LinearLayoutManager(LinkageActivity.this);
        mFirstRecycler.setLayoutManager(manager);
        mFirstRecycler.setAdapter(mFirstAdapter);

        mSecondRecycler.setLayoutManager(new LinearLayoutManager(LinkageActivity.this));
        mSecondAdapter = new SecondAdapter(this,mSecondList);
        mSecondRecycler.setAdapter(mSecondAdapter);

        mFirstAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int positon) {
                mSecondList.clear();
                switch (positon){
                    case 0:
                        mSecondList.add("博乐");
                        mSecondList.add("乌鲁木齐");
                        mSecondList.add("喀什");
                        mSecondList.add("五家渠");
                        break;
                    case 1:
                        mSecondList.add("成都");
                        mSecondList.add("重庆");
                        break;
                    case 2:
                        mSecondList.add("郑州");
                        mSecondList.add("乌鲁木齐");
                        mSecondList.add("喀什");
                        mSecondList.add("胡辣汤");
                        mSecondList.add("烩面");
                        break;
                    case 3:
                        mSecondList.add("兰州");
                        mSecondList.add("还是");
                        mSecondList.add("手段解决阿娇");
                        mSecondList.add("受打击啊");
                        mSecondList.add("视达");
                        mSecondList.add("撒大大");
                        mSecondList.add("视达是啊");
                        mSecondList.add("阿萨大大大");
                        break;
                }
                mSecondRecycler.setVisibility(View.VISIBLE);
                mSecondAdapter.notifyDataSetChanged();
            }
        });
    }
}
