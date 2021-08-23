package com.example.haha.paging3.pagingSample

sealed class CheeseListItem(val name:String){
    data class Item(val cheese:Cheese) : CheeseListItem(cheese.name)
    data class Separator(private val letter:Char)
        :CheeseListItem(letter.uppercaseChar().toString())
}
