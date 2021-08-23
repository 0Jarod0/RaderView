package com.example.haha.proxy

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class DynamicProxy(mProxy:Any) : InvocationHandler{

    private val mObject: Any = mProxy


    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
        return method?.invoke(mObject, args)
    }
}