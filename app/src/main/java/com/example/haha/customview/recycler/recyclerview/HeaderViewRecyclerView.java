package com.example.haha.customview.recycler.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haha.customview.recycler.adapter.HeaderViewListAdapter;

/**
 * Created by haha on 2020/4/27.
 * 装饰模式 给recyclerview添加头布局 和底布局
 */

public class HeaderViewRecyclerView extends RecyclerView {

    private HeaderViewListAdapter mAdapter;

    public HeaderViewRecyclerView(Context context) {
        super(context);
    }
    public HeaderViewRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public HeaderViewRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        mAdapter = new HeaderViewListAdapter(adapter);
        super.setAdapter(mAdapter);
    }

    public void addHeaderView(View view){
        if (mAdapter != null){
            mAdapter.addHeaderView(view);
        }
    }

    public void addFooterView(View view){
        if(mAdapter != null){
            mAdapter.addFooterView(view);
        }
    }
    public void removeHeaderView(View view){
        if (mAdapter != null){
            mAdapter.removeHeaderView(view);
        }
    }
    public void removeFooterView(View view){
        if (mAdapter != null){
            mAdapter.removeFooterView(view);
        }
    }
}
