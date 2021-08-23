package com.example.haha.assemle

fun printAll(strings:Collection<String>){
    for (s in strings)  print("$s")
    println()
}

//MutableCollection是一个具有写操作的Collection接口，例如add以及remove
fun List<String>.getShortWordsTo(shortWords:MutableList<String>,maxLength:Int){
    this.filterTo(shortWords){it.length <= maxLength}
    //throw away the articels
    val articles = setOf("a","A","an","An","the","The")
    shortWords -= articles
}
fun main() {
    val numbers = listOf("one","two","three","four")
    numbers.filter { it.length > 3 }
}