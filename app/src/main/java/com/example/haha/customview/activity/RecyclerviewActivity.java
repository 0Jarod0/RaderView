package com.example.haha.customview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.haha.customview.R;
import com.example.haha.customview.adapter.MainAdapter;
import com.example.haha.customview.recycler.AddHeaderActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haha on 2020/4/27.
 */

public class RecyclerviewActivity extends AppCompatActivity {

    private String[] data = {"addHeader"};
    private List<String> list = new ArrayList<>();
    private MainAdapter mAdapter;

    private ListView mList;
    private Intent mIntent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        initView();
    }

    private void initView(){
        mList = (ListView)findViewById(R.id.listview);
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
                        mIntent = new Intent(RecyclerviewActivity.this, AddHeaderActivity.class);
                        startActivity(mIntent);
                        break;
                }
            }
        });
    }
}
