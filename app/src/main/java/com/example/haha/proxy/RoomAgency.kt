package com.example.haha.proxy

//代理人
class RoomAgency(var room:IRoom) : IRoom{

    private val mRoom = room


    override fun seekRoom() {
        mRoom.seekRoom()
    }

    override fun watchRoom() {
        mRoom.watchRoom()
    }

    override fun room() {
        mRoom.room()
    }

    override fun finish() {
        mRoom.finish()
    }
}