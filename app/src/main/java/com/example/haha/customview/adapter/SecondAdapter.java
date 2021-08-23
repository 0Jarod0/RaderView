package com.example.haha.customview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.haha.customview.R;

import java.util.List;

/**
 * Created by haha on 2020/4/26.
 */

public class SecondAdapter extends RecyclerView.Adapter<SecondAdapter.Viewholder>{
    private Context mContext;
    private List<String> mList;

    public SecondAdapter(Context context,List<String> list){
        mContext = context;
        mList = list;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_first, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        holder.mTxt.setText(mList.get(position));
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        TextView mTxt;
        public Viewholder(View itemView) {
            super(itemView);
            mTxt = itemView.findViewById(R.id.txt);
        }
    }
}
