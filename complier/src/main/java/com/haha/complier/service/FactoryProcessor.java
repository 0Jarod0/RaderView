package com.haha.complier.service;

import com.google.auto.service.AutoService;
import com.haha.api.annotation.Factory;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;


@AutoService(Processor.class)

public class FactoryProcessor extends AbstractProcessor {

    //处理TypeMirror的工具类
    private Types typeUtils;

    //处理Element的工具类
    private Elements elementUtils;

    // 用来创建你要创建的文件
    private Filer filer;

    //提供给注解处理器一个报告错误、警告以及提示信息的途径。
    private Messager messager;

    private int count = 0;

    //类名和  FactoryGroupedClasses 的映射（一个接口映射一个 FactoryGroupedClasses，本例 Meal 的类名映射一个 FactoryGroupedClasses）
    private Map<String, FactoryGroupedClasses> factoryClasses = new LinkedHashMap<>();


    /**
     * 需要强调的是 Element 代表一个元素，包、类、方法、变量 这些都是 Element
     * 在注解的处理过程中会扫描所有的java源文件。源代码中的每一个部分都是一个特定类型的 Element
     * 可见 Foo 类，特意新建了一个类来说明
     */


    //初始化操作的方法，RoundEnvironment会提供很多有用的工具类Elements、Types和Filer等。
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();


    }
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotataions = new LinkedHashSet<>();
        annotataions.add(Factory.class.getCanonicalName());
        return annotataions;
    }






    //这相当于每个处理器的主函数main()。在该方法中去扫描、评估、处理以及生成Java文件。

    //    因此 Element代表的是源代码。TypeElement代表的是源代码中的类型元素，
    // 然而TypeElement并不包含类本身的信息
    // 你可以从TypeElement中获取类的名字，但是你获取不到类的信息，例如它的父类。
    // 这种信息需要通过TypeMirror获取。你可以通过调用elements.asType()获取元素的TypeMirror。

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnv) {
        // roundEnv.getElementsAnnotatedWith(Factory.class))返回所有被注解了@Factory的元素的列表
        //所有元素列表。也就是包括 类、包、方法、变量等
        //roundEnv.getElementsAnnotatedWith(Factory.class)

        count++;
        note(null,"process 方法执行了：%s次",count);


        //遍历所有的元素列表
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Factory.class)) {

//            note(annotatedElement,"该元素的TypeMirror是%s" ,(DeclaredType)(annotatedElement.asType()));

            //由于返回的是所有元素，所以还要判断该元素是不是一个类
            if (annotatedElement.getKind() != ElementKind.CLASS) {
                error(annotatedElement, "Only classes can be annotated with @%s",
                        Factory.class.getSimpleName());
                return true; // 退出处理
            }


            // Element 的元素类型是 ElementKind.CLASS，所以可以直接强制转换（实际上就是被@Factory 注解的类 ）

            TypeElement typeElement = (TypeElement) annotatedElement;




            try {
                //生成 FactoryAnnotatedClass，将 typeElement保存的 FactoryAnnotatedClass 中，
                // 之后用于判断该元素是否为符合我们要求的元素
                FactoryAnnotatedClass factoryAnnotatedClass = new FactoryAnnotatedClass(typeElement);

                /**
                 *
                 * 检测类是否符合要求：
                 * 1。是一个公开类
                 * 2。 只要一个公开的构造函数
                 * 3。 不是抽象类
                 * 4。继承于特定的类型，
                 * */
                if (!isValidClass(factoryAnnotatedClass)) {
                    return true; // 已经打印了错误信息，退出处理过程
                }

                //从缓存Map 里获取
                FactoryGroupedClasses factoryClass =
                        factoryClasses.get(factoryAnnotatedClass.getQualifiedFactoryGroupName());


                if (factoryClass == null) {

                    //Meal 的合法全名
                    String qualifiedGroupName = factoryAnnotatedClass.getQualifiedFactoryGroupName();

                    //用 Meal 的合法全名作为参数，生成 FactoryGroupedClasses
                    factoryClass = new FactoryGroupedClasses(qualifiedGroupName);

                    //用  Meal 的合法全名作为key  FactoryGroupedClasses的实例作为值存入Map
                    factoryClasses.put(qualifiedGroupName, factoryClass);
                }

                // 如果和其他的@Factory标注的类的id相同冲突，
                // 抛出IdAlreadyUsedException异常

                //将包含被@Factory 注解的类的信息的 FactoryAnnotatedClass
                // 的实例加入到 FactoryGroupedClasses 中的 map 中（经过多次循环，本项目会存入三个
                //分别为   Tiramisu ，Margherita，Calzone）


                factoryClass.add(factoryAnnotatedClass);

            } catch (IllegalArgumentException e) {
                // @Factory.id()为空 --> 打印错误信息
                error(typeElement, e.getMessage());
            } catch (ProcessingException e) {
                error(e.getElement(), e.getMessage());

            }



        }
        // 为每个工厂生成Java文件
        //本例只有一个工厂 Meal 工厂
        try {
            for(FactoryGroupedClasses factoryClass : factoryClasses.values()) {
                factoryClass.generateCode(elementUtils, filer);
            }
            // Clear to fix the problem,
            //虽然注解处理器只会初始化一次。但 process（）可能会多次调用
            // 在每个处理循环中，注解处理器会去处理存在该注解的所有文件，包括先前处理循环中生成的文件（这些文件可能也包含注解）。
            //如果factoryClasses数据没有清空，会重复生成代码，此时则会报错。所以在每个处理循环中需要清空map
            factoryClasses.clear();

        } catch (IOException e) {
            error(null, e.getMessage());
        }
        return true;
    }



    /**
     * 使用{@link  #messager } 工具打印错误消息
     */
    private void error(Element e, String msg, Object... args) {
        messager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(msg, args),
                e);
    }
    /**
     * 使用{@link  #messager } 工具打印信息
     */
    private void note(Element e, String msg, Object... args) {
        messager.printMessage(
                Diagnostic.Kind.NOTE,
                String.format(msg, args),
                e);
    }


    /**
     * 检查类{@link FactoryAnnotatedClass 是否符合要求}
     * <p>
     * 检测类是否符合要求：
     * 1。是一个公开类
     * 2。 只要一个公开的构造函数
     * 3。 不是抽象类
     * 4。继承于特定的类型，
     */
    private boolean isValidClass(FactoryAnnotatedClass item) {

        // 转换为TypeElement, 含有更多特定的方法（就是被@Factory 注解的类的类型）
        TypeElement classElement = item.getTypeElement();


        //是否是一个公开的类（class 前面的修饰语是否包含 PUBLIC）
        if (!classElement.getModifiers().contains(Modifier.PUBLIC)) {
            error(classElement, "The class %s is not public.",
                    classElement.getQualifiedName().toString());
            return false;
        }

        // 检查是否是一个抽象类（被 @Factory 注解的不能是个抽象类）
        if (classElement.getModifiers().contains(Modifier.ABSTRACT)) {
            error(classElement, "The class %s is abstract. You can't annotate abstract classes with @%",
                    classElement.getQualifiedName().toString(), Factory.class.getSimpleName());
            return false;
        }

        // 检查继承关系: 必须是@Factory.type()指定的类型子类(获取 Meal  TypeElement（类型元素）)
        TypeElement superClassElement =
                elementUtils.getTypeElement(item.getQualifiedFactoryGroupName());


        //是否是个接口 interface(这里指 Meal )
        if (superClassElement.getKind() == ElementKind.INTERFACE) {


            // 检查接口是否实现了
            //被 @Factory 注解的类实现的接口是否包含 superClassElement (这里指 Meal)
            if (!classElement.getInterfaces().contains(superClassElement.asType())) {
                error(classElement, "The class %s annotated with @%s must implement the interface %s",
                        classElement.getQualifiedName().toString(), Factory.class.getSimpleName(),
                        item.getQualifiedFactoryGroupName());
                return false;
            }
        }
        //如果()
        else {
            // 检查子类 ,classElement 是被@Factory 注解的类
            TypeElement currentClass = classElement;
            while (true) {
                TypeMirror superClassType = currentClass.getSuperclass();

                if (superClassType.getKind() == TypeKind.NONE) {
                    // 到达了基本类型(java.lang.Object), 所以退出
                    error(classElement, "The class %s annotated with @%s must inherit from %s",
                            classElement.getQualifiedName().toString(), Factory.class.getSimpleName(),
                            item.getQualifiedFactoryGroupName());
                    return false;
                }

                //父类就是要求的父类（这里指 Meal）
                if (superClassType.toString().equals(item.getQualifiedFactoryGroupName())) {
                    // 找到了要求的父类
                    break;
                }

                // 在继承树上继续向上搜寻
                currentClass = (TypeElement) typeUtils.asElement(superClassType);
            }
        }

        // 检查是否提供了默认公开构造函数（@被 Factory 注解的类是否 有 公开的构造 函数）
        for (Element enclosed : classElement.getEnclosedElements()) {
            if (enclosed.getKind() == ElementKind.CONSTRUCTOR) {
                ExecutableElement constructorElement = (ExecutableElement) enclosed;


                //构造函数是公开的，切构造函数参数为0
                if (constructorElement.getParameters().size() == 0 && constructorElement.getModifiers()
                        .contains(Modifier.PUBLIC)) {
                    // 找到了默认构造函数
                    return true;
                }
            }
        }

        // 没有找到默认构造函数
        error(classElement, "The class %s must provide an public empty default constructor",
                classElement.getQualifiedName().toString());
        return false;
    }
}


