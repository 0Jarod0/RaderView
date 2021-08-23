package com.example.haha.lambda

fun main() {
    val items = listOf(1,2,3,4)
    val list = mutableListOf(1,2,3)

    items.fold(0,{
        acc:Int,i:Int ->
        print("acc= $acc,i=$i,")
        val result = acc + i
        println("result=$result")
        result
    })

    list.swap(
            0,2
    )
    println(list)
}
fun <T,R> Collection<T>.fold(
        initial:R,
        combine:(acc:R,nextElement:T)-> R
):R{
    var accumulator:R = initial
    for (element:T in this){
        accumulator = combine(accumulator,element)
    }
    return accumulator
}

fun MutableList<Int>.swap(index1:Int,index2:Int){
    val tmp = this[index1]
    this[index1] = this[index2]
    this[index2] = tmp
}