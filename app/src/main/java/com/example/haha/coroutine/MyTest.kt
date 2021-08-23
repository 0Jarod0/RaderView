package com.example.haha.coroutine

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking{
    val time = measureTimeMillis {
        val one = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
        val two = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }
        println("The answer is ${one.await()+two.await()}")
    }
    println("Complete in $time ms")
}

suspend fun doSomethingUsefulOne():Int{
    delay(1000L)
    return 13
}

suspend fun doSomethingUsefulTwo():Int{
    delay(1000L)
    return 29
}