package com.example.haha.proxy
//被代理人
class XiaoMing : IRoom{
    override fun seekRoom() {
        println("找房")
    }

    override fun watchRoom() {
        println("看房")
    }

    override fun room() {
        println("给钱租房")
    }

    override fun finish() {
        println("完成租房")
    }
}