@file:Suppress("UNCHECKED_CAST")

package com.example.haha.paging3.pagingWithNet

import androidx.collection.LruCache
import retrofit2.Retrofit
import java.lang.IllegalArgumentException


object RetrofitClient {

    private var mInitDelegate:((Retrofit.Builder)-> Retrofit.Builder)?=null

    private val serviceHolder = LruCache<String, Any>(1024 * 1024)

    private val sRetrofitInstance: Retrofit by lazy {
        createRetrofitInstance().build()
    }

    @JvmStatic
    fun get(): Retrofit{
        return sRetrofitInstance
    }

    @JvmStatic
    fun <T> create(service: Class<T>):T{
        var serviceInstance = serviceHolder.get(service.name)
        if(serviceInstance == null){
            serviceInstance = get().create(service)
            serviceHolder.put(service.name, serviceInstance as Any)
        }
        return serviceInstance as T
    }


    fun init(initDelegate:(Retrofit.Builder)-> Retrofit.Builder){
        this.mInitDelegate = initDelegate
    }


    private fun createRetrofitInstance(): Retrofit.Builder {
        val initDelegate = mInitDelegate ?: throw IllegalArgumentException("RetrofitClient.init() must be call first")
        return initDelegate.invoke(Retrofit.Builder())
    }

}