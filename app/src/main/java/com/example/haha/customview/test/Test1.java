package com.example.haha.customview.test;

/**
 * Created by haha on 2020/7/9.
 */

public class Test1 {
    public static void main(String[] args){
        String str = "Java,Java,hello world!";
        String newStr = str.replaceAll("Java","尚硅谷");
        System.out.println("newStr="+newStr);
    }
}
