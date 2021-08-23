package com.haha.complier.service;

import com.haha.api.annotation.Factory;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

public class FactoryGroupedClasses {

    /**
     * 将被添加到生成的工厂类的名字中
     */
    private static final String SUFFIX = "Factory";


    /**
     * 本例指 Meal 的合法全名
     */
    private String qualifiedClassName;

    /**
     *
     * */
    private Map<String, FactoryAnnotatedClass> itemsMap =
            new LinkedHashMap<String, FactoryAnnotatedClass>();




    //用 Meal 的合法全名作为参数，生成 FactoryGroupedClasses
    public FactoryGroupedClasses(String qualifiedClassName) {
        this.qualifiedClassName = qualifiedClassName;
    }

    /**
     * 添加方法，向itemMap 中插入
     * {@link FactoryAnnotatedClass}
     */
    public void add(FactoryAnnotatedClass toInsert) throws ProcessingException {


        /**
         *查询 {@link itemsMap } 中是否有该id 的 FactoryAnnotatedClass
         * */
        FactoryAnnotatedClass existing = itemsMap.get(toInsert.getId());
        if (existing != null) {

            // Alredy existing
            throw new ProcessingException(toInsert.getTypeElement(),
                    "Conflict: The class %s is annotated with @%s with id ='%s' but %s already uses the same id",
                    toInsert.getTypeElement().getQualifiedName().toString(), Factory.class.getSimpleName(),
                    toInsert.getId(), existing.getTypeElement().getQualifiedName().toString());
        }

        itemsMap.put(toInsert.getId(), toInsert);

    }

    /**
     *
     *使用 Square javapoet 生成代码
     * */
    public void generateCode(Elements elementUtils, Filer filer) throws IOException {
        //通过工厂接口的合法全类名获取到工厂接口的 TypeElement（本例的工厂为 Meal）
        TypeElement superClassName = elementUtils.getTypeElement(qualifiedClassName);

        //获取生成的class 名称（本例生成的为 MealFactory）
        String factoryClassName = superClassName.getSimpleName() + SUFFIX;


        //获取包元素
        PackageElement pkg = elementUtils.getPackageOf(superClassName);

        //获取包名
        String packageName = pkg.isUnnamed() ? null : pkg.getQualifiedName().toString();

        //生成一个方法名：create，参数：String 类型  叫id，返回值
        MethodSpec.Builder method = MethodSpec.methodBuilder("create")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(String.class, "id")
                .returns(TypeName.get(superClassName.asType()));

        // check if id is null（生成判断id 是否为空的判断逻辑）
        method.beginControlFlow("if (id == null)")
                .addStatement("throw new IllegalArgumentException($S)", "id is null!")
                .endControlFlow();

        // Generate items map
        //遍历被注解的类的封装类 FactoryAnnotatedClass

        for (FactoryAnnotatedClass item : itemsMap.values()) {

            method.beginControlFlow("if ($S.equals(id))", item.getId())
                    .addStatement("return new $L()", item.getTypeElement().getQualifiedName().toString())
                    .endControlFlow();
        }


        method.addStatement("throw new IllegalArgumentException($S + id)", "Unknown id = ");

        TypeSpec typeSpec = TypeSpec.classBuilder(factoryClassName).addMethod(method.build()).build();

        // Write file
        JavaFile.builder(packageName, typeSpec).build().writeTo(filer);
    }


}
