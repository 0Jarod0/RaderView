package com.example.haha.customview.recycler.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by haha on 2020/4/27.
 */

public class HeaderViewListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    //装饰RecyclerView.Adapter
    private final RecyclerView.Adapter mRealAdapter;
    ArrayList<View> mHeaderViews;
    ArrayList<View> mFooterViews;

    public HeaderViewListAdapter(RecyclerView.Adapter mRealAdapter) {
        this.mRealAdapter = mRealAdapter;
        mRealAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                notifyDataSetChanged();
            }
        });
        mHeaderViews = new ArrayList<>();
        mFooterViews = new ArrayList<>();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        //头部返回 头部的ViewHolder
        int numHeaders = getHeadersCount();
        if (position < numHeaders){
            return createHeaderFooterViewHolder(mHeaderViews.get(position));
        }

        //mRealAdapter返回mRealAdapter的ViewHolder
        final int adjPosition = position - numHeaders;
        int adapterCount = 0;
        if (mRealAdapter != null){
            adapterCount = mRealAdapter.getItemCount();
            if (adjPosition < adapterCount){
                //直接传position,不兼容万能适配多布局条目
                return mRealAdapter.onCreateViewHolder(parent,mRealAdapter.getItemViewType(adjPosition));
            }
        }
        return createHeaderFooterViewHolder(mFooterViews.get(adjPosition - adapterCount));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //头部和尾部都是不需要做处理的，只要mRealAdapter要去做处理
        int numHeaders = getHeadersCount();
        if (position < numHeaders){
            return;
        }
        final int adjPosition = position - numHeaders;
        int adapterCount = 0;
        if (mRealAdapter != null){
            adapterCount = mRealAdapter.getItemCount();
            if (adjPosition < adapterCount){
                mRealAdapter.onBindViewHolder(holder,position);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        //把位置设置位viewType
        return position;
    }

    @Override
    public int getItemCount() {
        return mFooterViews.size()+mHeaderViews.size()+mRealAdapter.getItemCount();
    }

    public int getHeadersCount(){
        return mHeaderViews.size();
    }

    public int getFootersCount(){
        return mFooterViews.size();
    }

    private RecyclerView.ViewHolder createHeaderFooterViewHolder(View view){
        return new RecyclerView.ViewHolder(view) {};
    }

    public void addHeaderView(View view){
        if (!mHeaderViews.contains(view)){
            mHeaderViews.add(view);
            notifyDataSetChanged();
        }
    }
    public void addFooterView(View view){
        if (!mFooterViews.contains(view)){
            mFooterViews.add(view);
            notifyDataSetChanged();
        }
    }

    public void removeHeaderView(View view){
        if (mHeaderViews.contains(view)){
            mHeaderViews.remove(view);
            notifyDataSetChanged();
        }
    }
    public void removeFooterView(View view){
        if (mFooterViews.contains(view)){
            mFooterViews.remove(view);
            notifyDataSetChanged();
        }
    }
}
