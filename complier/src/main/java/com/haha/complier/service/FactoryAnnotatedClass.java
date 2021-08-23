package com.haha.complier.service;


import com.haha.api.annotation.Factory;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;

public class FactoryAnnotatedClass {

    /**
     * TypeElement 信息
     * */
    private TypeElement annotatedClassElement;

   /**
    *{@link Factory#type()}指定的类型合法全名 ，在我这个项目里指 app 项目里的 Meal
    * */
    private String qualifiedSuperClassName;


    private String simpleTypeName;

    /**
     * {@link Factory#id()}中指定的id
     * */
    private String id;

    /**
     * 在 FactoryAnnotatedClass 中，我们保存被注解类的数据，比如合法的类的名字，以及 @Factory 注解本身的一些信息。
     * 也就是说每一个使用了 @Factory 注解的类都对应一个 FactoryAnnotatedClass 类
     * */

    public FactoryAnnotatedClass(TypeElement classElement) throws IllegalArgumentException {


        //由于实质上就是被注解的类（比如本项目中的  Tiramisu）
        this.annotatedClassElement = classElement;

        //获取到注解
        Factory annotation = classElement.getAnnotation(Factory.class);

        //取出注解的中得id
        id = annotation.id();


        //如果id 为空则抛出错误
        if ("".equalsIgnoreCase(id)) {
            throw new IllegalArgumentException(
                    String.format("id() in @%s for class %s is null or empty! that's not allowed",
                            Factory.class.getSimpleName(), classElement.getQualifiedName().toString()));
        }

        // Get the full QualifiedTypeName

        try {

            //取出注解的中得 type，它是个 class，我们这个项目指定的type 为 Meal,也就是 Tiramisu 等类实现的接口
            Class<?> clazz = annotation.type();

            // 返回底层阶级Java语言规范中定义的标准名称。
            qualifiedSuperClassName = clazz.getCanonicalName();

            //获取简单的类名（Meal的简名）
            simpleTypeName = clazz.getSimpleName();


        } catch (MirroredTypeException mte) {
            DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
            TypeElement classTypeElement = (TypeElement) classTypeMirror.asElement();
            qualifiedSuperClassName = classTypeElement.getQualifiedName().toString();
            simpleTypeName = classTypeElement.getSimpleName().toString();
        }
    }

    /**
     * 获取在{@link Factory#id()}中指定的id
     * return the id
     */
    public String getId() {
        return id;
    }

    /**
     * 获取在{@link Factory#type()}指定的类型合法全名 ,本项目为Meal 的标准合法全名
     *
     * @return qualified name
     */
    public String getQualifiedFactoryGroupName() {
        return qualifiedSuperClassName;
    }


    /**
     * 获取在{@link Factory#type()}{@link Factory#type()}指定的类型的简单名字
     *
     * @return qualified name
     */
    public String getSimpleFactoryGroupName() {
        return simpleTypeName;
    }

    /**
     * 获取被  TypeElement（被@Factory 注解的类的 TypeElement）
     */
    public TypeElement getTypeElement() {
        return annotatedClassElement;
    }
}
