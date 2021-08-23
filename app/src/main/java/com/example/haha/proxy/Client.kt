package com.example.haha.proxy

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Proxy

fun main() {
    val xiaoming = XiaoMing()

    val dynamicProxy = DynamicProxy(xiaoming)

    val classLoader = xiaoming.javaClass.classLoader

    val roomAgency:IRoom = Proxy.newProxyInstance(classLoader
        , arrayOf(IRoom::class.java),dynamicProxy) as IRoom

    roomAgency.watchRoom()

    roomAgency.seekRoom()

    roomAgency.room()

    roomAgency.finish()
}