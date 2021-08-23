package com.example.haha.reflect;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Test {
    @RequiresApi(api = Build.VERSION_CODES.P)
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        Benz benz = new Benz();
        Class benzClass = Benz.class;
        Class benzClass1 = benz.getClass();
        Class benzClass2 = Class.forName("com.example.haha.reflect.Benz");
        System.out.println("benz->"+benz.getClass().getSimpleName());

        String classPath1 = benzClass.getName();
        String classPath2 = benzClass.getCanonicalName();
        System.out.println(classPath1);
        System.out.println(classPath2);

        Class<Benz.InnerClass> innerClass = Benz.InnerClass.class;
        System.out.println(innerClass.getName());
        System.out.println(innerClass.getCanonicalName());

        String fatherClassName = benzClass.getSuperclass().getSimpleName();
        System.out.println(fatherClassName);

        Class[] interfaces = benzClass.getInterfaces();
        for (Class aInterface:interfaces){
            System.out.println(aInterface.getName());
        }

        Constructor constructor = benzClass.getDeclaredConstructor();
        Benz myBenz = (Benz)constructor.newInstance();
        myBenz.carColor = "黑色";
        myBenz.combine();
        System.out.println(myBenz.carColor);

        Field carName = benzClass.getDeclaredField("carName");
        System.out.println(carName);

        Field[] declaredFields = benzClass.getDeclaredFields();
        for (Field declaredField:declaredFields){
            System.out.println("属性:"+declaredField.getName());
        }

        Field[] fields = benzClass.getFields();
        for (Field field:fields){
            System.out.println("属性:"+field.getName());
        }
        carName.setAccessible(true);
        System.out.println(carName.getType().getName());
        System.out.println(carName.get(benz));

        carName.set(benz,"sweetying");
        System.out.println(carName.get(benz));

        Method publicMethod = benzClass.getMethod("combine");
        System.out.println("public->"+publicMethod);
        Method privateMethod = benzClass.getDeclaredMethod("privateMethod", String.class);
        System.out.println("private->"+privateMethod);
        privateMethod.setAccessible(true);
        privateMethod.invoke(benz,"接收传入的参数");

        Method[] declaredMethods = benzClass.getDeclaredMethods();
        for (Method declareMethod:declaredMethods){
            System.out.println("方法名:"+declareMethod.getName());
        }

        Method[] methods = benzClass.getMethods();
        for(Method method:methods){
            System.out.println("公共方法:"+method.getName());
        }

        obtainGeneric();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private static void obtainGeneric() throws ClassNotFoundException {
        Class benzClass2 = Class.forName("com.example.haha.reflect.Benz");
        Type genericType = benzClass2.getGenericSuperclass();
        if (genericType instanceof ParameterizedType){
            Type[] actualType = ((ParameterizedType) genericType).getActualTypeArguments();
            for (Type type:actualType){
                System.out.println(type.getTypeName());
            }
        }
    }
}
