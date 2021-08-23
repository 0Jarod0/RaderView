package com.example.haha.data;

import com.example.haha.annotation.Message;

@Message(decr = "描述狗狗的特性的类",author = "zhang",age = 28)
public class Dog extends Animal{

    String name;
    String age;

    @Message(decr = "获取狗狗名称的方法",author = "zhang",age = 28)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
