package com.example.haha.reflect;

@CustomAnnotation3
public class Car<K,V> {
    private String carDesign = "设计稿";
    public String engine = "发动机";

    public void run(long kilometer){
        System.out.println("Car run"+kilometer+"km");
    }
}
