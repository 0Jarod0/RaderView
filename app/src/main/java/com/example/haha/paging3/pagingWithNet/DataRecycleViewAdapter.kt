package com.example.haha.paging3.pagingWithNet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.haha.customview.R

class DataRecycleViewAdapter:PagingDataAdapter<Data,RecyclerView.ViewHolder>(object :
DiffUtil.ItemCallback<Data>(){
    override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem == newItem
    }

}) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as DataViewHolder).text.text = getItem(position)?.desc
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_data_view,null)
        return DataViewHolder(view)
    }


 inner class DataViewHolder(view:View):RecyclerView.ViewHolder(view){
     val text:TextView = view.findViewById(R.id.tv_content)
 }
}