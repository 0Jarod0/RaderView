package com.example.haha.customview.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.haha.annotation.Message;
import com.example.haha.customview.R;
import com.example.haha.service.CalzonePizza;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Scanner;

/**
 *
 * @author haha
 * @date 2020/3/13
 */

public class WindowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        WindowManager mWindowManager = ((WindowManager) getSystemService(WINDOW_SERVICE));
//        Button mFloatingButton = new Button(this);
//        mFloatingButton.setText("button");
//
//        WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
//                , ViewGroup.LayoutParams.WRAP_CONTENT, 0, 0, PixelFormat.TRANSPARENT);
//
//        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
//                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
//        mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
//        mLayoutParams.x = 100;
//        mLayoutParams.y = 300;
//        mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG;
//        mWindowManager.addView(mFloatingButton, mLayoutParams);
        findAnnotationOnClass();

        findAnnotationOnMethod();

        new CalzonePizza();
    }

    private static String readPizzaNameFromConsole() {
        Scanner s = new Scanner(System.in);
        System.out.println("------请输入披萨名称：");
        String lin = "";
        lin = s.nextLine();
        System.out.println("------读取披萨名称结束！！");
        return lin;
    }

    /**
     * 找到类上的注解
     */
    private void findAnnotationOnClass(){
        try{
            //找到类
            Class aClass = Class.forName("com.example.haha.data.Dog");
            //找到类上的注解
            boolean isExist = aClass.isAnnotationPresent(Message.class);

            //如果存在 拿到注解
            if (isExist){
                Message messageAnnotation = (Message) aClass.getAnnotation(Message.class);
                System.out.println("类的描述为："+messageAnnotation.decr()
                        +",作者:"+messageAnnotation.author()+",年龄:"+messageAnnotation.age());
            }
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private void findAnnotationOnMethod(){
        try{
            Class<?> dogClass = Class.forName("com.example.haha.data.Dog");
            Method[] methods = dogClass.getMethods();

            getAnnotationValuesOne(methods);

            getAnnotationValuesTwo(methods);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private void getAnnotationValuesOne(Method[] methods){
        for (Method method:methods){
            boolean isExist = method.isAnnotationPresent(Message.class);
            if (isExist){
                Message message = method.getAnnotation(Message.class);

                System.out.println("该方法的描述为:"+message.decr()+
                        ",作者为:"+message.author()+",年龄:"+message.age());
            }
        }
    }

    private void getAnnotationValuesTwo(Method[] methods){
        for(Method method : methods){
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation:annotations){
                if (annotation instanceof Message){
                    Message message = (Message) annotation;
                    System.out.println("该方法的描述为:"+message.decr()+
                            ",作者为:"+message.author()+",年龄:"+message.age());
                }
            }
        }
    }
}
