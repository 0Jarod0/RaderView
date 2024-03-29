package com.example.haha.paging3.pagingSample

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.haha.customview.R

class CheeseViewHolder(parent:ViewGroup):RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.cheese_item
        , parent, false)
) {
    var cheese:Cheese?=null
    private set
    private val nameView = itemView.findViewById<TextView>(R.id.name)

    fun bindTo(item:CheeseListItem?){
        if (item is CheeseListItem.Separator){
            nameView.text = "${item.name}Cheeses"
            nameView.setTypeface(null,Typeface.BOLD)
        }else{
            nameView.text = item?.name
            nameView.setTypeface(null,Typeface.BOLD)
        }
        cheese = (item as? CheeseListItem.Item)?.cheese
        nameView.text = item?.name
    }
}