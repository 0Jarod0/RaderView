package com.example.haha.reflect;

public class Benz extends Car<String,Integer> implements ICar{
    private String carName = "奔驰";
    public String carColor = "白色";

    public Benz(){}

    public Benz(String carName){
        this.carName = carName;
    }

    public Benz(String carName,String carColor){
        this.carName = carName;
        this.carColor = carColor;
    }

    @Override
    public void combine() {
        System.out.println("组装一台奔驰");
    }

    private void privateMethod(String params){
        System.out.println("我是私有方法:"+params);
    }

    class InnerClass{

    }
}
