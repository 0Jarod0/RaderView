package com.example.haha.paging3.pagingSample

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil

class CheeseAdapter :PagingDataAdapter<CheeseListItem,CheeseViewHolder>(diffCallback) {
    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<CheeseListItem>(){
            override fun areItemsTheSame(
                oldItem: CheeseListItem,
                newItem: CheeseListItem
            ): Boolean {
                return if (oldItem is CheeseListItem.Item
                    && newItem is CheeseListItem.Item){
                    oldItem.cheese.id == newItem.cheese.id
                }else if (oldItem is CheeseListItem.Item
                    && newItem is CheeseListItem.Item){
                    oldItem.name == newItem.name
                }else{
                    oldItem == newItem
                }
            }

            override fun areContentsTheSame(
                oldItem: CheeseListItem,
                newItem: CheeseListItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: CheeseViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheeseViewHolder {
        return CheeseViewHolder(parent)
    }
}