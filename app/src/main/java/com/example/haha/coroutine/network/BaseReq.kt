package com.example.haha.coroutine.network

import java.io.Serializable

class BaseReq<T> : Serializable{

    private var data: T? = null

    /**
     * 业务信息
     */
    private var errorMsg = ""

    /**
     * 业务code
     */
    private var errorCode = 0

    fun data() : T{
        when(errorCode){
            //请求成功
            0,200 -> {
                return data!!
            }
            //未登录请求需要用户信息的接口
            -1001 -> {
                throw ApiException(errorMsg,errorCode)
            }
            //登录失败
            -1 -> {
                throw ApiException(errorMsg,errorCode)
            }
        }
        throw ApiException(errorMsg,errorCode)
    }
}