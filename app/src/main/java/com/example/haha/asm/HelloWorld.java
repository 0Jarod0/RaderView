package com.example.haha.asm;

public class HelloWorld {
    public void sayHello(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
