package com.example.haha.coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.Runnable

suspend fun performRequest(request:Int) :String{
    delay(1000)     //模仿长时间运行的异步工作
    return "response $request"
}

fun main() = runBlocking<Unit>{
    (1..5).asFlow()
        .filter {
            println("Filter $it")
            it%2==0 }
        .map {
            println("Map $it")
            "string $it"
        }.collect {
            println("Collect $it")
        }
}
